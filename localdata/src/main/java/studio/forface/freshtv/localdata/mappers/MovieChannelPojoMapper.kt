package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.localdata.MovieChannelPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [MovieChannelPojo]
 *
 * Inherit from [PojoMapper].
 */
class MovieChannelPojoMapper(): PojoMapper<MovieChannel, MovieChannelPojo>() {

    /** @see PojoMapper.toPojo */
    override fun MovieChannel.toPojo() = with(this ) {
        MovieChannelPojo.Impl( id, name, groupName, imageUrl?.s, mediaUrls, playlistPaths, tmdbId )
    }

    /** @see PojoMapper.toEntity */
    override fun MovieChannelPojo.toEntity() = with(this ) {
        MovieChannel( id, name, groupName, imageUrl?.let { Url( it ) }, mediaUrls, playlistPaths, tmdbId )
    }
}