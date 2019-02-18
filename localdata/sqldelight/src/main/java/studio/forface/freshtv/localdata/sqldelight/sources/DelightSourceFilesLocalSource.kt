package studio.forface.freshtv.localdata.sqldelight.sources

import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.localdata.sources.SourceFilesLocalSource
import studio.forface.freshtv.localdata.sqldelight.SourceFilePojo
import studio.forface.freshtv.localdata.sqldelight.SourceFileQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Playlists stored locally
 */
class DelightSourceFilesLocalSource(
    private val queries: SourceFileQueries
) : SourceFilesLocalSource<SourceFilePojo> {

    /** @return all the stored Source Files [SourceFilePojo] */
    override fun all() = queries.selectAll().executeAsList()

    /** @return all the stored Epgs [SourceFilePojo] */
    override fun allEpgs() = queries.selectAllByType( Epg.TYPE_NAME ).executeAsList()

    /** @return all the stored Playlists [SourceFilePojo] */
    override fun allPlaylists() = queries.selectAllByType( Playlist.TYPE_NAME ).executeAsList()

    /** Create a new Source File [SourceFilePojo] */
    override fun create( sourceFile: SourceFilePojo ) {
        with( sourceFile ) {
            queries.insert( path, type, name, sourceType )
        }
    }

    /** Delete all the stored Source Files [SourceFilePojo] */
    override fun deleteAll() {
        queries.deleteAll()
    }

    /** Delete the stored Source Files [SourceFilePojo] with the given [SourceFilePojo.path] */
    override fun delete( path: String ) {
        queries.delete( path )
    }

    /** @return the Epg [SourceFilePojo] with the given [path] */
    override fun epg( path: String ) =
        queries.selectByPath( Epg.TYPE_NAME, path ).executeAsOne()

    /** @return the Playlist [SourceFilePojo] with the given [path] */
    override fun playlist( path: String ) =
            queries.selectByPath( Playlist.TYPE_NAME, path ).executeAsOne()

    /** Update an already stored Source File [SourceFilePojo] */
    override fun update( sourceFile: SourceFilePojo ) {
        with( sourceFile ) {
            queries.update( path, name )
        }
    }
}