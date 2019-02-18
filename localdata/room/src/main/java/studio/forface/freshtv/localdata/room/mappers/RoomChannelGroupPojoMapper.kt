package studio.forface.freshtv.localdata.room.mappers

import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.localdata.mappers.ChannelGroupPojoMapper
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.room.entities.ChannelGroupPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [ChannelGroupPojo]
 *
 * Inherit from [ChannelGroupPojoMapper] and [PojoMapper].
 */
class RoomChannelGroupPojoMapper: ChannelGroupPojoMapper<ChannelGroupPojo> {

    /** @see PojoMapper.toPojo */
    override fun ChannelGroup.toPojo() = ChannelGroupPojo( id, name, type, imageUrl )

    /** @see PojoMapper.toEntity */
    override fun ChannelGroupPojo.toEntity() = ChannelGroup( name, type, imageUrl )
}