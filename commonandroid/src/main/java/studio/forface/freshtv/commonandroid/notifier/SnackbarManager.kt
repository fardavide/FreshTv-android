package studio.forface.freshtv.commonandroid.notifier

import com.google.android.material.snackbar.Snackbar
import studio.forface.freshtv.domain.gateways.Notifier

/**
 * @author Davide Giuseppe Farella
 * A class for show a [Snackbar]
 */
internal interface SnackbarManager {

    /** Creates and show a [Snackbar] with an option Action */
    fun showSnackBar( type: SnackBarType, message: CharSequence, action: Notifier.Action? )
}

/** An enum class for the types of the [Snackbar] */
enum class SnackBarType {
    Error, Info, Warn
}