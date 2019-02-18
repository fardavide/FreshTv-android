package studio.forface.freshtv.localdata.sqldelight.mappers

import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.localdata.mappers.ChannelGroupPojoMapper
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.sqldelight.ChannelGroupPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [ChannelGroupPojo]
 *
 * Inherit from [ChannelGroupPojoMapper] and [PojoMapper].
 */
class DelightChannelGroupPojoMapper: ChannelGroupPojoMapper<ChannelGroupPojo> {

    /** @see PojoMapper.toPojo */
    override fun ChannelGroup.toPojo() = with(this ) {
        ChannelGroupPojo.Impl( id, name, type.name, imageUrl?.s )
    }

    /** @see PojoMapper.toEntity */
    override fun ChannelGroupPojo.toEntity() = with(this ) {
        ChannelGroup( name, ChannelGroup.Type.valueOf( type ), imageUrl?.let { Url( it ) } )
    }
}