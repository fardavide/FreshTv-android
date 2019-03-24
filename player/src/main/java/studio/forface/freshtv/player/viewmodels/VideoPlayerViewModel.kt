package studio.forface.freshtv.player.viewmodels

import android.app.Application
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedAndroidViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.player.utils.mediaSource
import studio.forface.viewstatestore.*

/**
 * A View Model for play a Video.
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class VideoPlayerViewModel( application: Application ): ScopedAndroidViewModel( application ) {

    /** A [String] representing the current url to load into [player] */
    var currentUrl: String = EMPTY_STRING
        set( value ) {
            field = value

            val videoSource = mediaSource fromUrl currentUrl
            player.prepare( videoSource )
        }

    /** A [ViewStateStore] of [Nothing] delivering only [player]s errors via [ViewState.Error] */
    val errors = ViewStateStore<Nothing>()

    /** A [ViewStateStore] of [PlaybackState] */
    val playbackState = ViewStateStore<PlaybackState>()

    /** Instance of [SimpleExoPlayer] for play the video source */
    private val player: SimpleExoPlayer by lazy { ExoPlayerFactory.newSimpleInstance( context ) }

    /**
     * A [Player.EventListener] for observe [Player.EventListener.onPlayerError] and
     * [Player.EventListener.onPlayerStateChanged]
     */
    private val playerListener = object : Player.EventListener {

        /**
         * When the current source ( [currentUrl] ) fails to load ( whether is render error, web error, etc ), deliver
         * the [ExoPlaybackException] to [error] and deliver [PlaybackState.Error] to [playbackState]
         */
        override fun onPlayerError( error: ExoPlaybackException) {
            errors.setError( error )
            playbackState.setData( PlaybackState.Error( currentUrl ) )
        }

        /**
         * When the current source ( [currentUrl] ) succeed to load ( [playbackState] is [Player.STATE_READY] ), deliver
         * [PlaybackState.Success] to [playbackState]
         */
        override fun onPlayerStateChanged( playWhenReady: Boolean, state: Int ) {
            if ( state == Player.STATE_READY ) playbackState.setData( PlaybackState.Success( currentUrl ) )
        }
    }

    init {
        player.playWhenReady = true
        player.addListener( playerListener )
    }

    /** Set [PlayerView.setPlayer] for the given [playerView] */
    fun setPlayerView( playerView: PlayerView ) {
        playerView.player = player
    }

    /** When the `ViewModel` is cleared. */
    override fun onCleared() {
        player.stop(true )
        player.release()
        player.removeListener( playerListener )
        super.onCleared()
    }

    /** A sealed class representing the Success or the Failure of the loading of a stream for [currentUrl] */
    sealed class PlaybackState {
        abstract val url: String

        class Success( override val url: String ) : PlaybackState()
        class Error( override val url: String ) : PlaybackState()
    }
}