package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import studio.forface.freshtv.commonandroid.notifier.SnackbarManager
import studio.forface.freshtv.commonandroid.notifier.SnackbarType
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.utils.onFragmentResumed
import studio.forface.freshtv.domain.gateways.Notifier

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
): AppCompatActivity(), AndroidUiComponent, SnackbarManager {

    /**
     * The [FragmentManager.FragmentLifecycleCallbacks], here we will listen to [Fragment.onResume]
     * callbacks and we will unregister it when the Activity is stopped ( [BaseActivity.onStop] ).
     */
    private var fragmentLifecycleListener: FragmentManager.FragmentLifecycleCallbacks? = null

    /** @return the `Activity`s [NavController] */
    protected abstract val navController: NavController

    /** @return the `Activity`s root [View] */
    protected abstract val rootView: View

    /** @return the `Activity`s [Toolbar] */
    protected abstract val toolbar: Toolbar

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( layoutRes )
    }

    /** When the `Activity` is Started */
    override fun onStart() {
        super.onStart()
        notifier.snackbarManager = this
    }

    /** When the `Activity` is Stopped */
    override fun onStop() {
        notifier.snackbarManager = null
        super.onStop()
    }

    /** Called when a [BaseFragment] is resumed */
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

            if ( fragment !is BaseFragment ) return@onFragmentResumed
            onFragmentResumed( fragment )

            if ( fragment !is RootFragment ) return@onFragmentResumed

            fragment.title?.let { title = it }
            toolbar.setTitleTextColor( fragment.titleColor )
            fragment.setHasOptionsMenu( fragment.menuRes != null )
            setBackgroundColor( fragment.backgroundColor ?: getThemeColor( android.R.attr.colorBackground ) )
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