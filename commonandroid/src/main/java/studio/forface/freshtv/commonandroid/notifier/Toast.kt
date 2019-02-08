package studio.forface.freshtv.commonandroid.notifier

import android.content.Context
import android.view.Gravity
import com.fxn.cue.Cue
import com.fxn.cue.enums.Duration
import studio.forface.freshtv.commonandroid.utils.postOnUi
import com.fxn.cue.enums.Type as CueType

/**
 * @author Davide Giuseppe Farella.
 * A class that wraps [Cue] for show custom [android.widget.Toast] messages.
 */
class Toast( private val context: Context ) {

    /**
     * Show a [Toast] with the given [Type] and [message] to show to the user.
     * @param type [Type] of the [Toast].
     * @param message [CharSequence] message to show.
     */
    fun show( type: Type, message: CharSequence ) {
        val cue = Cue().with( context )
                .setCornerRadius( 20 )
                .setGravity( Gravity.CENTER )
                .setDuration( Duration.LONG )
                .setType( type.cue )
                .setMessage( message.toString() )

        context.postOnUi { cue.show() }
    }

    /** An enum class for the types of the [Toast] */
    enum class Type( internal val cue: CueType ) {
        Error ( CueType.DANGER ),
        Info ( CueType.INFO ),
        Warn ( CueType.WARNING )
    }
}