package studio.forface.freshtv.localdata.room.sources

import studio.forface.freshtv.domain.entities.SourceFile.*
import studio.forface.freshtv.localdata.room.dao.SourceFileDao
import studio.forface.freshtv.localdata.room.entities.SourceFilePojo
import studio.forface.freshtv.localdata.sources.SourceFilesLocalSource

/**
 * @author Davide Giuseppe Farella.
 * A source for Playlists stored locally
 */
class RoomSourceFilesLocalSource(
    private val dao: SourceFileDao
) : SourceFilesLocalSource<SourceFilePojo> {

    /** @return [List] of all the stored Source Files [SourceFilePojo] */
    override fun all() = dao.selectAll()

    /** @return [List] of all the stored Epgs [SourceFilePojo] */
    override fun allEpgs() = dao.selectAllByType( SourceFilePojo.Type.EPG )

    /** @return [List] of all the stored Playlists [SourceFilePojo] */
    override fun allPlaylists() = dao.selectAllByType( SourceFilePojo.Type.PLAYLIST )

    /** Create a new Source File [SourceFilePojo] */
    override fun create( sourceFile: SourceFilePojo) {
        dao.insert( sourceFile )
    }

    /** Delete all the stored Source Files [SourceFilePojo] */
    override fun deleteAll() {
        dao.deleteAll()
    }

    /** Delete the stored Source Files [SourceFilePojo] with the given [SourceFilePojo.path] */
    override fun delete( path: String ) {
        dao.delete( path )
    }

    /** @return the Epg [SourceFilePojo] with the given [path] */
    override fun epg( path: String ) = dao.selectByPath( SourceFilePojo.Type.EPG, path )

    /** @return the Playlist [SourceFilePojo] with the given [path] */
    override fun playlist( path: String ) = dao.selectByPath( SourceFilePojo.Type.PLAYLIST, path )

    /** Update an already stored Source File [SourceFilePojo] */
    override fun update( sourceFile: SourceFilePojo ) {
        dao.update( sourceFile )
    }
}