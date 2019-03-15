package studio.forface.freshtv.parsers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readBytes
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.parsers.FileContentResolver.Source
import java.io.*
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
    internal suspend operator fun invoke( type: SourceFile.Type, path: String ): String {
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
        suspend operator fun invoke( path: String ): String {
            return readBytes( path ).readDecompressedContent()
        }

        /** @return a [ByteArray] from the given [path] */
        suspend fun readBytes( path: String ): ByteArray

        /** @return a [String] from [ByteArray], also decompress if needed */
        private fun ByteArray.readDecompressedContent(): String {
            val needToDecompress =
                    this[0] == GZIPInputStream.GZIP_MAGIC.toByte() &&
                    this[1] == ( GZIPInputStream.GZIP_MAGIC ushr 8 ).toByte()

            return if ( needToDecompress )
                GZIPInputStream( inputStream() ).reader().readText()
            else String(this )
        }
    }

    /** A [Source] for retrieve the content of a Local file. */
    object Local : Source {

        /** @return a [ByteArray] from [File] */
        override suspend fun readBytes( path: String ) = File( path ).readBytes()
    }

    /** A [Source] for retrieve the content of a Remote file. */
    class Remote( private val client: HttpClient ): Source {

        /** @return a [ByteArray] from [HttpResponse] */
        override suspend fun readBytes( path: String ) = client.get<ByteArray>( path )
    }
}