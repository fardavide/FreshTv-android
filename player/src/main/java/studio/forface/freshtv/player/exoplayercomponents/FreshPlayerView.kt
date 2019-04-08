package studio.forface.freshtv.player.exoplayercomponents

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.exoplayer2.ui.PlayerView

/**
 * A custom implementation of [PlayerView]
 *
 * It inhibit the toggle of controller visibility at any touch event
 *
 * @author Davide Giuseppe Farella
 */
internal class FreshPlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PlayerView( context, attrs, defStyleAttr ) {

    /** Override [onTouchEvent] for avoid the spamming of [performClick] */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent( event: MotionEvent ): Boolean {
        return true
    }

    /** Toggle the visibility of the controller */
    fun toggleController() {
        if ( isControllerVisible ) hideController() else showController()
    }
}