package studio.forface.freshtv.player.ui

import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.*
import kotlinx.android.synthetic.main.fragment_player_video.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.uiModels.ChannelSourceUiModel
import studio.forface.freshtv.player.utils.PlayerErrorEventListener
import studio.forface.freshtv.player.utils.mediaSource
import studio.forface.freshtv.player.viewmodels.ChannelSourceViewModel


/**
 * A [NestedFragment] for the player of [PlayerFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class VideoFragment : NestedFragment<PlayerFragment>( R.layout.fragment_player_video ) {

    /** @return [String] Channel id from [rootFragment] */
    private val channelId by lazy { rootFragment.channelId }

    /** A [String] reference for keep track of the current url and call [onUrlLoadingFailed] */
    private lateinit var currentUrl: String

    /** Instance of [SimpleExoPlayer] for play the video source */
    private val player: SimpleExoPlayer by lazy { ExoPlayerFactory.newSimpleInstance( context ) }

    /** A [Player.EventListener] for call [onUrlLoadingFailed] or [onUrlLoadingSuccess] */
    private val playerListener = object : Player.EventListener {

        /**
         * Invoke [onUrlLoadingFailed] when the current source ( [currentUrl] ) fails to load ( whether is render error,
         * web error, etc )
         */
        override fun onPlayerError( error: ExoPlaybackException ) {
            onUrlLoadingFailed( currentUrl )
        }

        /**
         * Invoke [onUrlLoadingSuccess] when the current source ( [currentUrl] ) succeed to load ( [playbackState] is
         * [Player.STATE_READY] )
         */
        override fun onPlayerStateChanged( playWhenReady: Boolean, playbackState: Int ) {
            if ( playbackState == Player.STATE_READY ) onUrlLoadingSuccess( currentUrl )
        }
    }

    /** A reference to [ChannelSourceViewModel] */
    private val sourceViewModel by viewModel<ChannelSourceViewModel> { parametersOf( channelId ) }

    /** When the `Activity` is created for [VideoFragment] */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        sourceViewModel.source.observe {
            doOnData( ::onSourceReady )
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [View] is created for [PlayerFragment] */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        playerView.player = player
        player.playWhenReady = true
        player.addListener( playerListener )
    }

    /** When the [View] is destroyed for [PlayerFragment] */
    override fun onDestroyView() {
        player.removeListener( playerListener )
        super.onDestroyView()
    }

    /** When a [ChannelSourceUiModel] is received from [ChannelSourceViewModel] */
    private fun onSourceReady( source: ChannelSourceUiModel ) {
        currentUrl = source.url
        val videoSource = mediaSource fromUrl currentUrl
        player.prepare( videoSource )
    }

    /** When the player failed to load the given [url] */
    private fun onUrlLoadingFailed( url: String ) {
        sourceViewModel.notifyFailure( url )
    }

    /** When the player succeed to load the given [url] */
    private fun onUrlLoadingSuccess( url: String ) {
        sourceViewModel.notifySuccess( url )
    }
}