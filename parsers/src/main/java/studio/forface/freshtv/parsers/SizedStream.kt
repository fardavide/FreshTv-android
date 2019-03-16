package studio.forface.freshtv.parsers

import io.ktor.client.response.HttpResponse
import io.ktor.http.contentLength
import kotlinx.coroutines.io.jvm.javaio.toInputStream
import java.io.File
import java.io.InputStream

/**
 * A class that wraps an [InputStream] and its [size]
 *
 * @author Davide Giuseppe Farella
 */
internal data class SizedStream(
    val input: InputStream,
    val size: Long
)

/** @return a [SizedStream] from [File] */
internal fun File.toSizedStream() = SizedStream(
    inputStream(),
    length()
)

/** @return a [SizedStream] from an [HttpResponse] */
internal fun HttpResponse.toSizedStream() = SizedStream(
    content.toInputStream(),
    contentLength()!!
)