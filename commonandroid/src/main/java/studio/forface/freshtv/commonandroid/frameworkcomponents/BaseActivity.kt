package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import studio.forface.freshtv.commonandroid.notifier.SnackbarManager
import studio.forface.freshtv.commonandroid.notifier.SnackbarType
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.utils.onFragmentResumed
import studio.forface.freshtv.commonandroid.viewstate.ViewStateObserver
import studio.forface.freshtv.commonandroid.viewstate.ViewStateStore
import studio.forface.freshtv.domain.gateways.Notifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @author Davide Giuseppe Farella.
 * A base class for Activity's.
 *
 * @param layoutRes The [LayoutRes] of the Layout for our `Activity`
 *
 * Inherit from [AppCompatActivity].
 * Implements [AndroidUiComponent]
 * Implements [SnackbarManager]
 */
abstract class BaseActivity(
    @LayoutRes private val layoutRes: Int
): AppCompatActivity(), LifecycleOwner, AndroidUiComponent, SnackbarManager {

    /**
     * The [FragmentManager.FragmentLifecycleCallbacks], here we will listen to [Fragment.onResume]
     * callbacks and we will unregister it when the Activity is stopped ( [BaseActivity.onStop] ).
     */
    private var fragmentLifecycleListener: FragmentManager.FragmentLifecycleCallbacks? = null

    /** @return an OPTIONAL `Activity`s [FloatingActionButton] */
    open val fab: FloatingActionButton? = null

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
    protected fun onFragmentResumed( fragment: BaseFragment ) {  }

    /** If [navController] can't [NavController.navigateUp] call [onBackPressed] */
    override fun onNavigateUp() =
        navController.navigateUp() || false.also { onBackPressed() }

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
        fragmentLifecycleListener = supportFragmentManager.onFragmentResumed { fragment ->
            fragment.assertIsBaseFragment()
            onFragmentResumed( fragment )

            if ( fragment !is RootFragment ) return@onFragmentResumed

            // Title
            fragment.title?.let { titleTextView.text = it }
            titleTextView.setTextColor( fragment.titleColor )

            // Options Menu
            fragment.setHasOptionsMenu( fragment.menuRes != null )

            // Background
            setBackgroundColor(
                    fragment.backgroundColor ?: getThemeColor( android.R.attr.colorBackground )
            )

            // Fab
            fab?.let { fab ->
                val fabParams = fragment.fabParams
                if ( fabParams == null ) fab.hide()
                else {
                    fab.setImageResource( fabParams.drawableRes )
                    // fab.setText( fabParams.textRes ) // TODO Fab can't have a Text
                    fab.setOnClickListener( fabParams.action )
                    fab.show()
                }
            }
        }
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

    /** Call [ViewStateStore.observe] with `this` `Activity` as [LifecycleOwner] */
    inline fun <V> ViewStateStore<V>.observe( block: ViewStateObserver<V>.() -> Unit  ) =
            observe(this@BaseActivity, block )
}

/** Assert that the given [Fragment] is a [BaseFragment] */
@UseExperimental( ExperimentalContracts::class )
private fun Fragment.assertIsBaseFragment() {
    contract { returns() implies ( this@assertIsBaseFragment is BaseFragment ) }
    val fragment = this
    if ( fragment !is BaseFragment ) {
        val fragmentName = fragment::class.qualifiedName
        val baseFragmentName = BaseFragment::class.qualifiedName
        throw AssertionError("'$fragmentName' does not inherit from '$baseFragmentName'")
    }
}

/**
 * @return OPTIONAL [BaseActivity] from a [BaseFragment] ( optional since the
 * [BaseFragment.getActivity] is also nullable ).
 */
internal val BaseFragment.baseActivity get() = activity as? BaseActivity