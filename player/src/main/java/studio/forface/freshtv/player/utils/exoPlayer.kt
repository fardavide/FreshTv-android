package studio.forface.freshtv.player.utils

import android.content.Context
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.ui.VideoFragment
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.TransferListener

/** @return a [DefaultHttpDataSourceFactory] created from [exoPlayerUserAgent] */
private val Context.httpDataSourceFactory get() =
        HttpDataSourceFactory( exoPlayerUserAgent, allowCrossProtocolRedirects = true )

/**
 * @constructor for [DefaultHttpDataSourceFactory].
 * Its purpose is to offer named arguments since they're not available for Java constructor
 */
@Suppress("FunctionName")
internal fun HttpDataSourceFactory(
    userAgent: String,
    listener: TransferListener? = null,
    connectTimeoutMillis: Int = DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
    readTimeoutMillis: Int = DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
    allowCrossProtocolRedirects: Boolean = false
) = DefaultHttpDataSourceFactory(
    userAgent, listener, connectTimeoutMillis, readTimeoutMillis, allowCrossProtocolRedirects
)

/** @return a [String] user agent for `Exo Player` */
private val Context.exoPlayerUserAgent get() = Util.getUserAgent(this, getString( R.string.app_name ) )

/** @return a new instance of [MediaSourceFactory] */
internal val VideoFragment.mediaSource get() = MediaSourceFactory( requireContext() )

/** A class for create [MediaSource] for `Exo Player` */
internal class MediaSourceFactory( private val context: Context ) {

    /** @return an [ExtractorMediaSource] from the given [url] */
    internal infix fun fromUrl( url: String ) = ExtractorMediaSource.Factory( context.httpDataSourceFactory )
            .createMediaSource( url.toUri() )
}

/** A [Player.EventListener] that listen to [Player.EventListener.onPlayerError] and delivers to [onError] lambda */
internal class PlayerErrorEventListener( private val onError: (ExoPlaybackException) -> Unit ): Player.EventListener {

    /** When an [ExoPlaybackException] occurs, call [onError] */
    override fun onPlayerError( error: ExoPlaybackException ) {
        onError( error )
    }
}

/** A [Player.EventListener] that listen to [Player.EventListener.onPlayerError] and delivers to [onError] lambda */
internal class PlayerSurceEventListener( private val onError: (ExoPlaybackException) -> Unit ): Player.EventListener {

    /** When an [ExoPlaybackException] occurs, call [onError] */
    override fun onPlayerError( error: ExoPlaybackException ) {
        onError( error )
    }
}