package studio.forface.freshtv.localdata.sqldelight.mappers

import studio.forface.freshtv.domain.entities.TvChannelWithGuide
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.mappers.TvChannelWithGuidePojoMapper
import studio.forface.freshtv.localdata.sqldelight.utils.TvChannelWithGuidePojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [TvChannelWithGuidePojo]
 *
 * Inherit from [TvChannelWithGuidePojoMapper].
 */
class DelightTvChannelWithGuidePojoMapper: TvChannelWithGuidePojoMapper<TvChannelWithGuidePojo> {

    /** @see PojoMapper.toPojo */
    override fun TvChannelWithGuide.toPojo() = with(this ) {
        TvChannelWithGuidePojo( id, name, imageUrl?.s, favorite, program?.title, program?.startTime, program?.endTime )
    }

    /** @see PojoMapper.toEntity */
    override fun TvChannelWithGuidePojo.toEntity() = with(this ) {
        val program = if ( programTitle != null && programStatTime != null && programEndTime != null )
            TvChannelWithGuide.Program( programTitle!!, programStatTime!!, programEndTime!! )
        else null
        TvChannelWithGuide( id, name, imageUrl?.let { Url( it ) }, favorite, program )
    }
}