package studio.forface.freshtv.parsers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.parsers.FileContentResolver.Source
import java.io.File
import java.io.InputStream
import java.util.zip.GZIPInputStream

/**
 * @author Davide Giuseppe Farella
 * A class for retrieve the content of a given [SourceFile] from the appropriate [Source]
 */
internal class FileContentResolver(
    private val local: FileContentResolver.Local = Local,
    private val remote: FileContentResolver.Remote = Remote( HttpClient() )
) {

    /** @return the [String] content of the given [path] with the given [type] */
    internal suspend fun readString( type: SourceFile.Type, path: String ): String {
        val source = when( type ) {
            SourceFile.Type.LOCAL -> local
            SourceFile.Type.REMOTE -> remote
        }
        return source.readString( path )
    }

    /** @return the [SizedStream] from the given [path] with the given [type] */
    internal suspend operator fun invoke( type: SourceFile.Type, path: String ): SizedStream {
        val source = when( type ) {
            SourceFile.Type.LOCAL -> local
            SourceFile.Type.REMOTE -> remote
        }
        return source( path )
    }

    /** @return the [String] content of the given [Playlist] */
    suspend operator fun invoke( playlist: Playlist ) = this ( playlist.type, playlist.path )

    /** @return the [String] content of the given [Epg] */
    suspend operator fun invoke( epg: Epg ) = this ( epg.type, epg.path )


    /** An interface for retrieve the content of a given [String] path */
    interface Source {

        /** @return the [String] content of the given [path] */
        suspend fun readString( path: String ): String {
            return readBytes( path ).readDecompressedContent()
        }

        /** @return the [SizedStream] from given [path] */
        suspend operator fun invoke( path: String ): SizedStream {
            return with( readStream( path ) ) {
                copy( input = input.decompress() )
            }
        }

        /** @return a [ByteArray] from the given [path] */
        suspend fun readBytes( path: String ): ByteArray

        /** @return a [SizedStream] from the given [path] */
        suspend fun readStream( path: String ): SizedStream

        /** @return a [String] from [ByteArray], also decompress if needed */
        private fun ByteArray.readDecompressedContent(): String {
            val needToDecompress =
                this[0] == GZIPInputStream.GZIP_MAGIC.toByte() &&
                this[1] == ( GZIPInputStream.GZIP_MAGIC ushr 8 ).toByte()

            return if ( needToDecompress )
                GZIPInputStream( inputStream() ).reader().readText()
            else String(this )
        }

        /** @return a [InputStream] and wrap to [GZIPInputStream] if needed */
        private fun InputStream.decompress(): InputStream {
            return with( buffered() ) {
                // Mark the initial position of the Stream
                mark( 0 )
                // Read two bytes for check if it is a GZIP
                val header = ByteArray(2 )
                read( header )
                val needToDecompress =
                    header[0] == GZIPInputStream.GZIP_MAGIC.toByte() &&
                    header[1] == ( GZIPInputStream.GZIP_MAGIC ushr 8 ).toByte()

                // Reset to the initial position
                reset()

                if ( needToDecompress )GZIPInputStream( this )
                else this
            }
        }
    }

    /** A [Source] for retrieve the content of a Local file. */
    object Local : Source {

        /** @return a [ByteArray] from [File] */
        override suspend fun readBytes( path: String ) = File( path ).readBytes()

        /** @return an [SizedStream] from [File] */
        override suspend fun readStream( path: String ) = File( path ).toSizedStream()
    }

    /** A [Source] for retrieve the content of a Remote file. */
    class Remote( private val client: HttpClient ): Source {

        /** @return a [ByteArray] from [HttpResponse] */
        override suspend fun readBytes( path: String ) = client.get<ByteArray>( path )

        /** @return a [SizedStream] from [HttpResponse] */
        override suspend fun readStream( path: String ) = client.get<HttpResponse>( path ).toSizedStream()
    }
}