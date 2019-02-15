package studio.forface.freshtv.commonandroid.notifier

import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.view.get
import androidx.core.view.setPadding
import com.fxn.cue.ColorRes
import com.fxn.cue.ColorRes.*
import com.fxn.cue.Cue
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import studio.forface.freshtv.commonandroid.utils.dp
import studio.forface.freshtv.domain.gateways.Notifier

/**
 * @author Davide Giuseppe Farella
 * A class for show a [Snackbar]
 */
internal interface SnackbarManager {

    /** Creates and show a [Snackbar] with an option Action */
    fun showSnackbar( type: SnackbarType, message: CharSequence, action: Notifier.Action? )

    /** @return the [TextView] of the message of the [Snackbar] */
    val Snackbar.messageView: TextView get() {
        val viewGroup = view as ViewGroup
        val contentLayout = viewGroup[0] as SnackbarContentLayout
        with ( contentLayout.javaClass.getDeclaredField("messageView" ) ) {
            isAccessible = true
            val messageView = get( contentLayout ) as TextView
            isAccessible = false
            return messageView
        }
    }

    /** Style and show the [Snackbar] */
    fun Snackbar.show( type: SnackbarType ) {
        view.setPadding( 8.dp )
        val ( background, border, text ) = getColors( type )

        val gradient = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor( background )
            setStroke(1, border )
        }
        view.background = gradient
        messageView.setTextColor( text )
        setActionTextColor( text )

        show()
    }

    /**
     * @return a [ColorSet] for the given [SnackbarType]
     * Copied from [Cue.getShape] - Colors from [ColorRes]
     */
    private fun getColors( type: SnackbarType ): ColorSet {
        return when ( type ) {
            SnackbarType.Error ->   danger_background_color     c danger_border_color   c danger_text_color
            SnackbarType.Info ->    info_background_color       c info_border_color     c info_text_color
            SnackbarType.Warn ->    warning_background_color    c warning_border_color  c warning_text_color
            // Type.SUCCESS ->         success_background_color    c success_border_color    c success_text_color
            // Type.PRIMARY ->         primary_background_color    c primary_border_color    c primary_text_color
            // Type.SECONDARY ->       secondary_background_color  c secondary_border_color  c secondary_text_color
            // Type.LIGHT ->           light_background_color      c light_border_color      c light_text_color
            // Type.DARK ->            dark_background_color       c dark_border_color       c dark_text_color
            // Type.CUSTOM ->          someCustomColor             c someCustomColor         c someCustomColor
                // shape.setColor( custom_background_color)
                // shape.setStroke( borderWidth, custom_border_color)
                // custom_text.setTextColor( custom_text_color)

        }
    }

    /** A data class for contain colors of the [Snackbar] */
    private data class ColorSet(
        @ColorInt internal val background: Int,
        @ColorInt internal val border: Int,
        @ColorInt internal val text: Int
    )

    /**
     * A data class that works as holder of 2 colors required from [ColorSet], it is useful for easily create a
     * [ColorSet].
     * i.e. `val colorSet = color1 c color2 c color3`
     */
    private data class ColorPair(
        @ColorInt internal val background: Int,
        @ColorInt internal val border: Int
    )

    /** An infix function for create a [ColorPair] from 2 [Int]s */
    private infix fun Int.c( other: Int ) = ColorPair(this, other )
    /** An infix function for create a [ColorSet] from a [ColorPair] and an [Int] */
    private infix fun ColorPair.c( other: Int ) = ColorSet( background, border, other )
}

/** An enum class for the types of the [Snackbar] */
enum class SnackbarType {
    Error, Info, Warn
}