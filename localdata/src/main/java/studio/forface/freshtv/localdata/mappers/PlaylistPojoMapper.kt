package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.domain.entities.Playlist
import studio.forface.freshtv.localdata.PlaylistPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [PlaylistPojo]
 *
 * Inherit from [PojoMapper].
 */
class PlaylistPojoMapper(): PojoMapper<Playlist, PlaylistPojo>() {

    /** @see PojoMapper.toPojo */
    override fun Playlist.toPojo() = with(this ) {
        PlaylistPojo.Impl( path, type.name, name )
    }

    /** @see PojoMapper.toEntity */
    override fun PlaylistPojo.toEntity() = with(this ) {
        Playlist( path, Playlist.Type.valueOf( type ), name )
    }
}