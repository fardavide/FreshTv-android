package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.localdata.ChannelGroupPojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [ChannelGroupPojo]
 *
 * Inherit from [PojoMapper].
 */
class ChannelGroupPojoMapper(): PojoMapper<ChannelGroup, ChannelGroupPojo>() {

    /** @see PojoMapper.toPojo */
    override fun ChannelGroup.toPojo() = with(this ) {
        ChannelGroupPojo.Impl( id, name, type.name, imageUrl?.s )
    }

    /** @see PojoMapper.toEntity */
    override fun ChannelGroupPojo.toEntity() = with(this ) {
        ChannelGroup( name, ChannelGroup.Type.valueOf( type ), imageUrl?.let { Url( it ) } )
    }
}