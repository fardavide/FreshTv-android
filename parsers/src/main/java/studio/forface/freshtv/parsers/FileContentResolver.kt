package studio.forface.freshtv.parsers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.gateways.UriResolver
import studio.forface.freshtv.parsers.FileContentResolver.Source
import java.io.File
import java.io.InputStream
import java.util.zip.GZIPInputStream

/**
 * @author Davide Giuseppe Farella
 * A class for retrieve the content of a given [SourceFile] from the appropriate [Source]
 */
internal class FileContentResolver(
    private val local: FileContentResolver.Local,
    private val remote: FileContentResolver.Remote
) {

    /** @return the [InputStream] from the given [path] with the given [type] */
    internal suspend operator fun invoke( type: SourceFile.Type, path: String ): InputStream {
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

        /** @return the [InputStream] from given [path] */
        suspend operator fun invoke( path: String ): InputStream {
            return readStream( path ).decompress()
        }

        /** @return a [InputStream] from the given [path] */
        suspend fun readStream( path: String ): InputStream

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
    class Local( private val uriResolver: UriResolver ) : Source {

        /** @return an [InputStream] from [File] */
        override suspend fun readStream( path: String ) = uriResolver( path )
    }

    /** A [Source] for retrieve the content of a Remote file. */
    class Remote( private val client: HttpClient = HttpClient() ): Source {

        /** @return a [InputStream] from [HttpResponse] */
        override suspend fun readStream( path: String ) = client.get<InputStream>( path )
    }
}