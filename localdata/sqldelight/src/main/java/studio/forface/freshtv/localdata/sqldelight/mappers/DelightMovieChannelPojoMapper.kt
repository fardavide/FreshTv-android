package studio.forface.freshtv.localdata.sqldelight.mappers

import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.localdata.mappers.MovieChannelPojoMapper
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.sqldelight.MovieChannelPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [MovieChannelPojo]
 *
 * Inherit from [MovieChannelPojoMapper] and [PojoMapper].
 */
class DelightMovieChannelPojoMapper: MovieChannelPojoMapper<MovieChannelPojo> {

    /** @see PojoMapper.toPojo */
    override fun MovieChannel.toPojo() = with(this ) {
        MovieChannelPojo.Impl( id, name, groupName, imageUrl?.s, mediaUrls, playlistPaths, favorite, tmdbId )
    }

    /** @see PojoMapper.toEntity */
    override fun MovieChannelPojo.toEntity() = with(this ) {
        MovieChannel( id, name, groupName, imageUrl?.let { Url( it ) }, mediaUrls, playlistPaths, favorite, tmdbId )
    }
}