package studio.forface.freshtv.player

import com.google.android.exoplayer2.source.UnrecognizedInputFormatException
import com.google.android.exoplayer2.upstream.HttpDataSource
import studio.forface.viewstatestore.ErrorStateGenerator
import studio.forface.viewstatestore.ViewState
import java.net.MalformedURLException
import java.net.UnknownHostException

/** [ErrorStateGenerator] for `player` module */
val playerErrorStateGenerator: ErrorStateGenerator = { throwable ->
    when {

        ( throwable.cause as? HttpDataSource.InvalidResponseCodeException )?.responseCode == 400 ->
            BadRequestError( throwable )

        ( throwable.cause as? HttpDataSource.InvalidResponseCodeException )?.responseCode == 403 ->
            ForbiddenAccessError( throwable )

        throwable.cause?.cause is MalformedURLException -> MalformedUrlError( throwable )

        ( throwable.cause as? HttpDataSource.InvalidResponseCodeException )?.responseCode == 404 ->
            StreamNotFoundError( throwable )

        throwable.cause is UnrecognizedInputFormatException -> UnsupportedStreamFormatError( throwable )

        throwable.cause?.cause is UnknownHostException -> UnknownHostError( throwable )

        else -> default
    }
}

/** A [ViewState.Error] for [HttpDataSource.InvalidResponseCodeException.responseCode] 400 */
private class BadRequestError( throwable: Throwable ): ViewState.Error( throwable ) {
    override val customMessageRes get() = R.string.bad_request
}

/** A [ViewState.Error] for [HttpDataSource.InvalidResponseCodeException.responseCode] 403 */
private class ForbiddenAccessError( throwable: Throwable ): ViewState.Error( throwable ) {
    override val customMessageRes get() = R.string.forbidden_access
}

/** A [ViewState.Error] for [MalformedURLException] */
private class MalformedUrlError( throwable: Throwable ): ViewState.Error( throwable ) {
    override val customMessageRes get() = when( throwable.cause?.cause?.message ) {
        "unknown protocol: rtmp" -> R.string.unsupported_rtmp_stream
        "unknown protocol: rtsp" -> R.string.unsupported_rtsp_stream
        else -> R.string.malformed_url
    }
}

/** A [ViewState.Error] for [HttpDataSource.InvalidResponseCodeException.responseCode] 404 */
private class StreamNotFoundError( throwable: Throwable ): ViewState.Error( throwable ) {
    override val customMessageRes get() = R.string.stream_not_found
}

/** A [ViewState.Error] for [UnknownHostException] */
private class UnknownHostError( throwable: Throwable ): ViewState.Error( throwable ) {
    override val customMessageRes get() = R.string.unknown_host
}

/** A [ViewState.Error] for [UnrecognizedInputFormatException] */
private class UnsupportedStreamFormatError( throwable: Throwable ): ViewState.Error( throwable ) {
    override val customMessageRes get() = R.string.unsupported_stream_format
}
