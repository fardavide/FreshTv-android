package studio.forface.freshtv.commonandroid.ui

/**
 * An interface for a UI item that can have a fullscreen state.
 * 
 * @author Davide Giuseppe Farella
 */
interface FullscreenEnabled {

    /** A [Boolean] value for keep track whether the UI item is in fullscreen */
    var isFullscreen: Boolean

    /** Enter in fullscreen */
    fun enterFullscreen()

    /** Exit the fullscreen */
    fun exitFullscreen()
}