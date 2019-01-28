package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.localdata.TvChannelPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [TvChannelPojo]
 *
 * Inherit from [PojoMapper].
 */
class TvChannelPojoMapper(): PojoMapper<TvChannel, TvChannelPojo>() {

    /** @see PojoMapper.toPojo */
    override fun TvChannel.toPojo() = with(this ) {
        TvChannelPojo.Impl( id, name, groupName, imageUrl?.s, mediaUrls, playlistPaths )
    }

    /** @see PojoMapper.toEntity */
    override fun TvChannelPojo.toEntity() = with(this ) {
        TvChannel( id, name, groupName, imageUrl?.let { Url( it ) }, mediaUrls, playlistPaths )
    }
}