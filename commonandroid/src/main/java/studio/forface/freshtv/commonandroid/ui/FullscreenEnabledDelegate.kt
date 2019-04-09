package studio.forface.freshtv.commonandroid.ui

import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity

/**
 * A Delegate for an UI element that implements [FullscreenEnabledComponent]
 * 
 * @author Davide Giuseppe Farella
 */
class FullscreenEnabledDelegate : FullscreenEnabledComponent {

    /** A strong reference to the [FragmentActivity] */
    private lateinit var activity : FragmentActivity

    /** @return the appropriate UI Flags for fullscreen mode */
    private val fullscreenFlags: Int
        get() = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )

    /** The initial flags when the UI item is crated or displayed */
    private var initialFlags: Int = 0

    /**
     * A [Boolean] value for keep track whether the UI item is in fullscreen
     *
     * Default value is false.
     */
    override var isFullscreen: Boolean = false

    /**
     * Initialize this delegate with [FragmentActivity]
     *
     * NOTE: To call when UI Element is resumed!
     */
    override fun initFullscreenEnabledDelegate( activity: FragmentActivity ) {
        this.activity = activity
        initialFlags = activity.window.decorView.systemUiVisibility
    }

    /**
     * INTERNAL USE ONLY, PUBLIC BECAUSE INTERFACE!
     * Enter if fullscreen.
     */
    override fun enterFullscreen() {
        isFullscreen = true
        with( activity.window ) {
            decorView.systemUiVisibility = fullscreenFlags
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    /**
     * INTERNAL USE ONLY, PUBLIC BECAUSE INTERFACE!
     * Exit the fullscreen
     */
    override fun exitFullscreen() {
        isFullscreen = false
        with( activity.window ) {
            decorView.systemUiVisibility = initialFlags
            clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }
}