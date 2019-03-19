package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import studio.forface.freshtv.commonandroid.notifier.SnackbarManager
import studio.forface.freshtv.commonandroid.notifier.SnackbarType
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.utils.onFragmentLifecycle
import studio.forface.freshtv.domain.gateways.Notifier
import studio.forface.freshtv.domain.utils.optLet
import studio.forface.freshtv.domain.utils.optWith
import studio.forface.materialbottombar.doOnPanelClose
import studio.forface.materialbottombar.doOnPanelState
import studio.forface.materialbottombar.dsl.MaterialPanel
import studio.forface.materialbottombar.layout.MaterialBottomDrawerLayout
import studio.forface.materialbottombar.layout.PanelChangeListener
import studio.forface.materialbottombar.set
import studio.forface.theia.dsl.TheiaActivity
import studio.forface.viewstatestore.ViewStateActivity
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


/**
 * @author Davide Giuseppe Farella.
 * A base class for Activity's.
 *
 *
 * Inherit from [TheiaActivity]
 *
 * Implements [AndroidUiComponent] for be able to retrieve some instance like [notifier]
 *
 * Implements [SnackbarManager] for handle the [Snackbar] messages
 *
 * Implements [ViewStateActivity] for `ViewStateStore` extensions
 *
 *
 * @param layoutRes The [LayoutRes] of the Layout for our `Activity`
 */
abstract class BaseActivity(
    @LayoutRes private val layoutRes: Int
): TheiaActivity(), LifecycleOwner, AndroidUiComponent, SnackbarManager, ViewStateActivity {

    /** @return the `Activity`s [MaterialBottomDrawerLayout] */
    protected abstract val drawerLayout: MaterialBottomDrawerLayout

    /**
     * The [FragmentManager.FragmentLifecycleCallbacks], here we will listen to [Fragment.onResume]
     * callbacks and we will unregister it when the Activity is stopped ( [BaseActivity.onStop] ).
     */
    private var fragmentLifecycleListener: FragmentManager.FragmentLifecycleCallbacks? = null

    /** @return the `Activity`s [FloatingActionButton] */
    abstract val fab: FloatingActionButton

    private var isDrawerOpen = false

    /** @return the `Activity`s [NavController] */
    protected abstract val navController: NavController

    /** @return the `Activity`s root [View] */
    protected abstract val rootView: CoordinatorLayout

    /** @return the `Activity`s [TextView] for the title */
    protected abstract val titleTextView: TextView

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setFragmentLifecycleListener()
        setContentView( layoutRes )
        drawerLayout.doOnPanelState { _, state ->
            isDrawerOpen = state != MaterialBottomDrawerLayout.Fly.BOTTOM
        }
    }

    /** When the `Activity` is Started */
    override fun onStart() {
        super.onStart()
        notifier.snackbarManager = this
        setFragmentLifecycleListener()
    }

    /** When the `Activity` is Stopped */
    override fun onStop() {
        notifier.snackbarManager = null
        supportFragmentManager.unregisterFragmentLifecycleCallbacks( fragmentLifecycleListener!! )
        fragmentLifecycleListener = null
        super.onStop()
    }

    /** Called when a [BaseFragment] is resumed */
    @Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")
    protected fun onFragmentResumed( fragment: BaseFragment ) {
        if ( fragment is RootFragment ) {

            // If fabParams is null, hide and reset fab. We call it now for avoid glitches
            fun resetFabIfNeeded() {
                if ( fragment.fabParams == null ) {
                    fab.hide()
                    fab.setImageBitmap( null )
                    fab.setOnClickListener( null )
                }
            }

            // Title
            fun setTitle() {
                fragment.title?.let { titleTextView.text = it }
                titleTextView.setTextColor( fragment.titleColor )
            }

            // Options Menu
            fun setOptionsMenu() = fragment.setHasOptionsMenu( fragment.menuRes != null )

            // Background
            fun setBg() =
                setBackgroundColor( fragment.backgroundColor ?: getThemeColor( android.R.attr.colorBackground ) )

            // Fab
            fun setFab() {
                optWith( fragment.fabParams ) {
                    fab.setImageResource( drawableRes )
                    // fab.setText( textRes ) // TODO Fab can't have a Text, but an extended Fab would be great
                    fab.setOnClickListener( action )
                }
            }
            fun showOrHideFab() =
                if ( fragment.fabParams?.showOnStart == true ) fab.show() else fab.hide()

            // Lambda to be called after bars hide
            val setAfterHide = {
                setTitle()
                setOptionsMenu()
                setBg()
                setFab()
            }

            // Lambda to be called after bars show
            val setAfterShow = {
                showOrHideFab()
            }

            // Hide and Show bars with setAfterHide and showAfterHide actions
            val hideAndShowBars: PanelChangeListener = {
                resetFabIfNeeded()
                drawerLayout.hideAndShowBars( true, doAfterHide = setAfterHide, doAfterShow = setAfterShow )
            }

            // If isDrawerOpen run hideAndShowBars after close, else run it now
            if ( isDrawerOpen ) drawerLayout.doOnPanelClose( once = true, callback = hideAndShowBars )
            else hideAndShowBars( 0 )
        }
    }

    /** If [navController] can't [NavController.navigateUp] call [onBackPressed] */
    override fun onNavigateUp() =
        navController.navigateUp() || false.also { onBackPressed() }

    /** Close the Soft Keyboard */
    fun closeKeyboard() {
        val inputMethodManager = getSystemService<InputMethodManager>() ?: return
        // Find the currently focused view, so we can grab the correct window token from it.
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        val view = currentFocus ?: View(this )
        inputMethodManager.hideSoftInputFromWindow( view.windowToken,0 )
        view.clearFocus()
    }

    /** Close a [MaterialPanel] and remove it from [drawerLayout] */
    fun dismissAndRemovePanel( panelId: Int ) {
        drawerLayout.closePanel()
        drawerLayout.removePanel( panelId )
    }

    /** Close a [MaterialPanel] */
    fun dismissPanel() {
        drawerLayout.closePanel()
    }

    /**
     * Set a background color to `Activity`.
     * @param color the [ColorInt] color to set as background.
     */
    private fun setBackgroundColor( @ColorInt color: Int ) {
        rootView.setBackgroundColor( color )
    }

    /**
     * Set a background color resource to `Activity`.
     * @param colorId the [ColorRes] resource of the color to set as background.
     */
    fun setBackgroundColorRes( @ColorRes colorId: Int ) {
        rootView.setBackgroundResource( colorId )
    }

    /**
     * Register the [fragmentLifecycleListener] on the [getSupportFragmentManager].
     * It will be unregistered when the `Activity` is stopped ( [BaseActivity.onStop] ).
     */
    private fun setFragmentLifecycleListener() {
        if ( fragmentLifecycleListener != null ) return

        fragmentLifecycleListener = supportFragmentManager.onFragmentLifecycle {

            // When Fragment is Paused, call closeKeyboard
            onPaused { closeKeyboard() }

            // When Fragment is Resumed, call onFragmentResumed
            onResumed { onFragmentResumed( it.assertIsBaseFragment() ) }
        }
    }

    /** Add the given [MaterialPanel] to [drawerLayout] and open it. */
    fun showPanel( panelId: Int, panel: MaterialPanel ) {
        drawerLayout[panelId] = panel
        drawerLayout.openPanel( panelId )
    }

    /**
     * Show a [Snackbar] with the given params
     * @param type the [SnackbarType], e.g. [SnackbarType.Error]
     * @param message the [CharSequence]main message of the [Snackbar]
     * @param action an OPTIONAL [Notifier.Action] for the [Snackbar]
     */
    override fun showSnackbar( type: SnackbarType, message: CharSequence, action: Notifier.Action? ) {
        val snackBar = Snackbar.make( rootView, message, Snackbar.LENGTH_LONG )
        action?.let { snackBar.setAction( action.name ) { action.block() } }
        snackBar.show( type )
    }
}

/** Assert that the given [Fragment] is a [BaseFragment] */
@UseExperimental( ExperimentalContracts::class )
private fun Fragment.assertIsBaseFragment() : BaseFragment {
    contract { returns() implies ( this@assertIsBaseFragment is BaseFragment ) }
    val fragment = this
    if ( fragment !is BaseFragment ) {
        val fragmentName = fragment::class.qualifiedName
        val baseFragmentName = BaseFragment::class.qualifiedName
        throw AssertionError("'$fragmentName' does not inherit from '$baseFragmentName'" )
    }
    return fragment
}

/**
 * @return OPTIONAL [BaseActivity] from a [BaseFragment] ( optional since the
 * [BaseFragment.getActivity] is also nullable ).
 */
internal val BaseFragment.baseActivity get() = activity as? BaseActivity