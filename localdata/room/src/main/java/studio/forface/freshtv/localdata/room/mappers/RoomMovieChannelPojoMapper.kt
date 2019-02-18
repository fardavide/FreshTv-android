package studio.forface.freshtv.localdata.room.mappers

import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.localdata.mappers.MovieChannelPojoMapper
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.room.entities.MovieChannelPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [MovieChannelPojo]
 *
 * Inherit from [MovieChannelPojoMapper] and [PojoMapper].
 */
class RoomMovieChannelPojoMapper: MovieChannelPojoMapper<MovieChannelPojo> {

    /** @see PojoMapper.toPojo */
    override fun MovieChannel.toPojo() =
        MovieChannelPojo( id, name, groupName, imageUrl, mediaUrls, playlistPaths, favorite, tmdbId )

    /** @see PojoMapper.toEntity */
    override fun MovieChannelPojo.toEntity() =
        MovieChannel( id, name, groupName, imageUrl, mediaUrls, playlistPaths, favorite, tmdbId )
}