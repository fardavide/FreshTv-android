package studio.forface.freshtv.localdata.sources

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.*
import studio.forface.freshtv.localdata.SourceFilePojo
import studio.forface.freshtv.localdata.SourceFileQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Playlists stored locally
 */
class SourceFilesLocalSource( private val queries: SourceFileQueries ) {

    /** @return all the stored Source Files [SourceFilePojo] */
    fun all() = queries.selectAll().executeAsList()

    /** @return all the stored Epgs [SourceFilePojo] */
    fun allEpgs() = queries.selectAllByType( Epg.TYPE_NAME ).executeAsList()

    /** @return all the stored Playlists [SourceFilePojo] */
    fun allPlaylists() = queries.selectAllByType( Playlist.TYPE_NAME ).executeAsList()

    /** Create a new Source File [SourceFilePojo] */
    fun create( sourceFile: SourceFilePojo ) {
        with( sourceFile ) {
            queries.insert( path, type, name, souceType )
        }
    }

    /** Delete all the stored Source Files [SourceFilePojo] */
    fun deleteAll() {
        queries.deleteAll()
    }

    /** Delete the stored Source Files [SourceFilePojo] with the given [SourceFilePojo.path] */
    fun delete( path: String ) {
        queries.delete( path )
    }

    /** @return the Epg [SourceFilePojo] with the given [path] */
    fun epg( path: String ) =
        queries.selectByPath( Epg.TYPE_NAME, path ).executeAsOne()

    /** @return the Playlist [SourceFilePojo] with the given [path] */
    fun playlist( path: String ) =
        queries.selectByPath( Playlist.TYPE_NAME, path ).executeAsOne()

    /** Update an already stored Source File [SourceFilePojo] */
    fun update( sourceFile: SourceFilePojo ) {
        with( sourceFile ) {
            queries.update( path, name )
        }
    }
}