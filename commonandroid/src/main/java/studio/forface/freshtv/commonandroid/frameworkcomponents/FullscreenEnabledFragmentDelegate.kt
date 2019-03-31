package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.RotationEnabled.Rotation

/**
 * A Delegate for a [Fragment] that implements [FullscreenEnabledFragment]
 * 
 * @author Davide Giuseppe Farella
 */
class FullscreenEnabledFragmentDelegate : FullscreenEnabledFragment {

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
     * Initialize this delegate
     *
     * NOTE: To call when [Fragment] is resumed!
     */
    override fun initFullscreenEnabledFragmentDelegate( fragment: Fragment ) {
        activity = fragment.requireActivity()
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