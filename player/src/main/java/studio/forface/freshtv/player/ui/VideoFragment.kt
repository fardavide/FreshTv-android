package studio.forface.freshtv.player.ui

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.view.postDelayed
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.*
import kotlinx.android.synthetic.main.fragment_player_video.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.commonandroid.frameworkcomponents.*
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.exoplayercomponents.FreshPlayerView
import studio.forface.freshtv.player.uiModels.ChannelSourceUiModel
import studio.forface.freshtv.player.viewmodels.ChannelSourceViewModel
import studio.forface.freshtv.player.viewmodels.VideoPlayerViewModel


/**
 * A [NestedFragment] for the player of [PlayerFragment]
 *
 * Implements [FullscreenEnabledFragment]
 * Implements [RotationEnabledFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class VideoFragment : NestedFragment<PlayerFragment>( R.layout.fragment_player_video ),
    FullscreenEnabledFragment by FullscreenEnabledFragmentDelegate(),
    RotationEnabledFragment by RotationEnabledFragmentDelegate() {

    /** @return [String] Channel id from [parentBaseFragment] */
    private val channelId by lazy { parentBaseFragment.channelId }

    /** A reference to [VideoPlayerViewModel] */
    private val playerViewModel by viewModel<VideoPlayerViewModel>()

    /** A reference to [ChannelSourceViewModel] */
    private val sourceViewModel by viewModel<ChannelSourceViewModel> { parametersOf( channelId ) }

    /** When the `Activity` is created for [VideoFragment] */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        // source from SourceViewModel
        sourceViewModel.source.observe {
            doOnData( ::onSourceReady )
            doOnError { notifier.error( it ) }
        }

        // screenLock from PlayerViewModel
        playerViewModel.screenLock.observeData( playerView::setKeepScreenOn )

        // sourceState from PlayerViewModel
        playerViewModel.sourceState.observeData {
            when ( it ) {
                is VideoPlayerViewModel.SourceState.Success -> onUrlLoadingSuccess( it.url )
                is VideoPlayerViewModel.SourceState.Error -> onUrlLoadingFailed( it.url )
            }
        }

        // errors from PlayerViewModel
        playerViewModel.errors.observe {
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [View] is created for [PlayerFragment] */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        playerViewModel.setPlayerView( playerView )

        val gestureListener = PlayerViewGestureListener(
            onClick = { playerView.toggleController() },
            onPinchIn = { playerView.resizeMode = RESIZE_MODE_FIXED_WIDTH },
            onPinchOut = { playerView.resizeMode = RESIZE_MODE_FIT }
        )
        val scaleGestureDetector = ScaleGestureDetector( context, gestureListener )
        val otherGestureDetector = GestureDetector( context, gestureListener )
        playerView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent( event )
            otherGestureDetector.onTouchEvent( event )
        }
    }

    /** When [VideoFragment] is resumed */
    override fun onResume() {
        initFullscreenEnabledFragmentDelegate(this )
        initRotationEnabledFragmentDelegate(this )
        super.onResume()

        if ( isLandscape() ) enterFullscreen()
        else exitFullscreen()
    }

    /** When a [ChannelSourceUiModel] is received from [ChannelSourceViewModel] */
    private fun onSourceReady( source: ChannelSourceUiModel ) {
        // TODO set playerView controls regarding source.type
        playerViewModel.currentUrl = source.url

        videoPlayerUrlTextView.apply {
            visibility = View.VISIBLE
            text = source.url
            postDelayed(3_000 ) { visibility = View.GONE }
        }
    }

    /** When the player failed to load the given [url] */
    private fun onUrlLoadingFailed( url: String ) {
        sourceViewModel.notifyFailure( url )
    }

    /** When the player succeed to load the given [url] */
    private fun onUrlLoadingSuccess( url: String ) {
        sourceViewModel.notifySuccess( url )
    }

    /**
     * Gesture listener for [FreshPlayerView]
     *
     * Inherit from [GestureDetector.SimpleOnGestureListener]
     * Inherit from [ScaleGestureDetector.OnScaleGestureListener]
     *
     * @param onPinchIn A lambda that will be called when a pinch in ( zoom in ) is detected
     * @param onPinchOut A lambda that will be called when a pinch out ( zoom out ) is detected
     */
    private class PlayerViewGestureListener(
        private val onClick: () -> Unit = {},
        private val onPinchIn: () -> Unit = {},
        private val onPinchOut: () -> Unit = {}
    ) : GestureDetector.SimpleOnGestureListener(), ScaleGestureDetector.OnScaleGestureListener {

        /** A [Boolean] for keep track whether a pinch event is occurring */
        private var isPinchInProgress = false

        /** On single tap trigger [onClick] */
        override fun onSingleTapUp( event: MotionEvent ): Boolean {
            onClick()
            return super.onSingleTapUp( event )
        }

        /** Listen to [onScaleBegin] and set [isPinchInProgress] to `true` */
        override fun onScaleBegin( detector: ScaleGestureDetector ): Boolean {
            isPinchInProgress = true
            return true
        }

        /** @see ScaleGestureDetector.SimpleOnScaleGestureListener.onScale */
        override fun onScale( detector: ScaleGestureDetector ) = false

        /** Listen to the [ScaleGestureDetector.getScaleFactor] and trigger [onPinchIn] or [onPinchOut] if needed */
        override fun onScaleEnd( detector: ScaleGestureDetector ) {
            isPinchInProgress = false
            val scaleFactor = detector.scaleFactor
            when {
                scaleFactor > 1 -> onPinchIn()
                scaleFactor < 1 -> onPinchOut()
            }
        }
    }
}