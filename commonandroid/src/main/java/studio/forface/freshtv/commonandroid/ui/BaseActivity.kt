package studio.forface.freshtv.commonandroid.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidUiComponent
import studio.forface.freshtv.commonandroid.notifier.SnackbarManager
import studio.forface.freshtv.commonandroid.notifier.SnackbarType
import studio.forface.freshtv.commonandroid.utils.onFragmentLifecycle
import studio.forface.freshtv.domain.gateways.Notifier
import studio.forface.theia.dsl.TheiaActivity
import studio.forface.viewstatestore.ViewStateActivity
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * A base class for Activity's.
 *
 * @param layoutRes The [LayoutRes] of the Layout for our `Activity`
 *
 * Inherit from [TheiaActivity]
 * Implements [AndroidUiComponent] for be able to retrieve some instance like [notifier]
 * Implements [SnackbarManager] for handle the [Snackbar] messages
 * Implements [ViewStateActivity] for `ViewStateStore` extensions
 *
 * @author Davide Giuseppe Farella
 */
abstract class BaseActivity(
    @LayoutRes private val layoutRes: Int
): TheiaActivity(), AndroidUiComponent, SnackbarManager, ViewStateActivity {

    /**
     * The [FragmentManager.FragmentLifecycleCallbacks], here we will listen to [Fragment.onResume]
     * callbacks and we will unregister it when the Activity is stopped ( [NavActivity.onStop] ).
     */
    private var fragmentLifecycleListener: FragmentManager.FragmentLifecycleCallbacks? = null

    /** @return the `Activity`s root [View] */
    protected abstract val rootView: CoordinatorLayout

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setFragmentLifecycleListener()
        setContentView( layoutRes )
    }

    /** When the `Activity` is Started */
    override fun onStart() {
        super.onStart()
        notifier.setSnackbarManager( this )
        setFragmentLifecycleListener()
    }

    /** Called when a [BaseFragment] is resumed */
    protected open fun onFragmentResumed( fragment: BaseFragment) {}

    /** When the `Activity` is Stopped */
    override fun onStop() {
        notifier.removeSnackbarManager(this )
        supportFragmentManager.unregisterFragmentLifecycleCallbacks( fragmentLifecycleListener!! )
        fragmentLifecycleListener = null
        super.onStop()
    }

    /** Close the Soft Keyboard */
    fun closeKeyboard() {
        val inputMethodManager = getSystemService<InputMethodManager>() ?: return
        // Find the currently focused view, so we can grab the correct window token from it.
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        val view = currentFocus ?: View(this )
        inputMethodManager.hideSoftInputFromWindow( view.windowToken,0 )
        view.clearFocus()
    }

    /**
     * Register the [fragmentLifecycleListener] on the [getSupportFragmentManager].
     * It will be unregistered when the `Activity` is stopped ( [NavActivity.onStop] ).
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
    contract { returns() implies ( this@assertIsBaseFragment is BaseFragment) }
    val fragment = this
    if ( fragment !is BaseFragment) {
        val fragmentName = fragment::class.qualifiedName
        val baseFragmentName = BaseFragment::class.qualifiedName
        throw AssertionError("'$fragmentName' does not inherit from '$baseFragmentName'" )
    }
    return fragment
}

/**
 * @return OPTIONAL [BaseActivity] from a [BaseFragment] ( optional since the [BaseFragment.getActivity] is also
 * nullable ).
 */
internal val BaseFragment.baseActivity get() = activity as? BaseActivity