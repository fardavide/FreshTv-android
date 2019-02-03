package studio.forface.freshtv.parsers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.parsers.FileContentResolver.Source
import java.io.File

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
    suspend operator fun invoke( epg: Epg ) =  this ( epg.type, epg.path )


    /** An interface for retrieve the content of a given [String] path */
    interface Source {

        /** @return the [String] content of the given [path] */
        suspend operator fun invoke( path: String ): String
    }


    /** A [Source] for retrieve the content of a Local file. */
    object Local : Source {
        override suspend fun invoke( path: String ) = File( path ).readText()
    }

    /** A [Source] for retrieve the content of a Remote file. */
    class Remote( private val client: HttpClient ): Source {
        override suspend fun invoke( path: String ) = client.get<String>( path )
    }
}