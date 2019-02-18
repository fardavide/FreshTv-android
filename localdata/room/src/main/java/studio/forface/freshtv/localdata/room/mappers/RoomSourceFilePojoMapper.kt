package studio.forface.freshtv.localdata.room.mappers

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.localdata.room.entities.SourceFilePojo
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.mappers.SourceFilePojoMapper

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [SourceFilePojo]
 *
 * Inherit from [SourceFilePojoMapper] and [PojoMapper].
 */
class RoomSourceFilePojoMapper: SourceFilePojoMapper<SourceFilePojo> {

    /** @see PojoMapper.toPojo */
    override fun SourceFile.toPojo() = with(this ) {
        val entityType = when( this ) {
            is SourceFile.Epg ->      SourceFilePojo.Type.EPG
            is SourceFile.Playlist -> SourceFilePojo.Type.PLAYLIST
        }
        SourceFilePojo( path, entityType, name, type )
    }

    /** @see PojoMapper.toEntity */
    override fun SourceFilePojo.toEntity() = with(this ) {
        when ( type ) {
            SourceFilePojo.Type.EPG ->      SourceFile.Epg( path, sourceType, name )
            SourceFilePojo.Type.PLAYLIST -> SourceFile.Playlist( path, sourceType, name )
        }
    }
}