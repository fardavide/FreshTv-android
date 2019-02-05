package studio.forface.freshtv.commonandroid

import android.content.Context
import android.view.Gravity
import androidx.annotation.StringRes
import com.fxn.cue.Cue
import com.fxn.cue.enums.Duration
import com.fxn.cue.enums.Type
import studio.forface.freshtv.commonandroid.utils.postOnUi

/**
 * @author Davide Giuseppe Farella.
 * A class that wraps [Cue] for show custom [android.widget.Toast] messages.
 */
class Toast( private val context: Context ) {

    /**
     * Show a custom [Toast] with a message for the user.
     * @param type the [Type] of the [Toast].
     * @param message the message to show.
     */
    private fun show( type: Type, message: String ) {
        val cue = Cue().with( context )
                .setCornerRadius( 20 )
                .setGravity( Gravity.CENTER )
                .setDuration( Duration.LONG )
                .setType( type )
                .setMessage( message )

        context.postOnUi { cue.show() }
    }

    /**
     * Show a [message] to the user with a custom [type]
     * @param message a [String] representing the message to show to the user.
     */
    fun custom( type: Type, message: String ) {
        show( type, message )
    }

    /**
     * @see custom
     * @param messageRes a [StringRes] that will be resolved in a [String].
     */
    fun custom( type: Type, @StringRes messageRes: Int ) {
        custom( type, context.getString( messageRes ) )
    }

    /**
     * Show an error [message] to the user.
     * @param message a [String] representing the message to show to the user.
     */
    fun error( message: String ) {
        show( Type.DANGER, message )
    }

    /**
     * @see error
     * @param messageRes a [StringRes] that will be resolved in a [String].
     */
    fun error( @StringRes messageRes: Int ) {
        error( context.getString( messageRes ) )
    }

    /**
     * Show an information message to the user.
     * @param message a [String] representing the message to show to the user.
     */
    fun info( message: String ) {
        show( Type.INFO, message )
    }

    /**
     * @see info
     * @param messageRes a [StringRes] that will be resolved in a [String].
     */
    fun info( @StringRes messageRes: Int ) {
        info( context.getString( messageRes ) )
    }

    /**
     * Show a warning message to the user.
     * @param message a [String] representing the message to show to the user.
     */
    fun warn( message: String ) {
        show(Type.WARNING, message)
    }

    /**
     * @see warn
     * @param message a [StringRes] that will be resolved in a [String].
     */
    fun warn( @StringRes message: Int ) {
        warn( context.getString( message ) )
    }
}