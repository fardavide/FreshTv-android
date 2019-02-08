package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import studio.forface.freshtv.commonandroid.notifier.SnackbarManager
import studio.forface.freshtv.commonandroid.notifier.SnackbarType
import studio.forface.freshtv.domain.gateways.Notifier

/**
 * @author Davide Giuseppe Farella.
 * A base class for Activity's.
 *
 * Inherit from [AppCompatActivity].
 * Implements [AndroidUiComponent]
 * Implements [SnackbarManager]
 */
abstract class BaseActivity: AppCompatActivity(), AndroidUiComponent, SnackbarManager {

    /** The root [View] */
    abstract val rootView: View

    /** WHen the Activity is Started */
    override fun onStart() {
        super.onStart()
        notifier.snackbarManager = this
    }

    /** WHen the Activity is Stopped */
    override fun onStop() {
        notifier.snackbarManager = null
        super.onStop()
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