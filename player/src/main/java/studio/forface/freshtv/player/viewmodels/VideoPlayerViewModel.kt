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

    /** A [LockedViewStateStore] of [Nothing] delivering only [player]s errors via [ViewState.Error] */
    val errors = ViewStateStore<Nothing>().lock

    /** A [LockedViewStateStore] of [Boolean] representing whether the player is currently playing */
    val playingState = ViewStateStore<Boolean>().lock

    /** A [LockedViewStateStore] of [SourceState] */
    val sourceState = ViewStateStore<SourceState>().lock

    /** Instance of [SimpleExoPlayer] for play the video source */
    private val player: SimpleExoPlayer by lazy { ExoPlayerFactory.newSimpleInstance( context ) }

    /**
     * A [Player.EventListener] for observe [Player.EventListener.onPlayerError] and
     * [Player.EventListener.onPlayerStateChanged]
     */
    private val playerListener = object : Player.EventListener {

        /**
         * When the current source ( [currentUrl] ) fails to load ( whether is render error, web error, etc ), deliver
         * the [ExoPlaybackException] to [error] and deliver [SourceState.Error] to [sourceState]
         */
        override fun onPlayerError( error: ExoPlaybackException) {
            errors.setError( error )
            sourceState.setData( SourceState.Error( currentUrl ) )
        }

        /**
         * When the current source ( [currentUrl] ) succeed to load ( [sourceState] is [Player.STATE_READY] ), deliver
         * [SourceState.Success] to [sourceState]
         */
        override fun onPlayerStateChanged( playWhenReady: Boolean, state: Int ) {
            // If Ready set success for sourceState
            if ( state == Player.STATE_READY )
                sourceState.setData( SourceState.Success( currentUrl ) )

            // Update playingState
            val running = ! ( state == Player.STATE_IDLE || state == Player.STATE_ENDED || ! playWhenReady )
            playingState.setData( running )
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
    sealed class SourceState {
        abstract val url: String

        class Success( override val url: String ) : SourceState()
        class Error( override val url: String ) : SourceState()
    }
}