package studio.forface.freshtv.localdata.room.mappers

import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.mappers.TvChannelPojoMapper
import studio.forface.freshtv.localdata.room.entities.TvChannelPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [TvChannelPojo]
 *
 * Inherit from [PojoMapper] and [TvChannelPojoMapper].
 */
class RoomTvChannelPojoMapper: TvChannelPojoMapper<TvChannelPojo> {

    /** @see PojoMapper.toPojo */
    override fun TvChannel.toPojo() =
        TvChannelPojo( id, name, groupName, imageUrl, mediaUrls, playlistPaths, favorite )

    /** @see PojoMapper.toEntity */
    override fun TvChannelPojo.toEntity() =
        TvChannel( id, name, groupName, imageUrl, mediaUrls, playlistPaths, favorite )
}