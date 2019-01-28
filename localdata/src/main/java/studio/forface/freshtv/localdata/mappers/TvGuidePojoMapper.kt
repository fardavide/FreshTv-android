package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.entities.TvGuide
import studio.forface.freshtv.localdata.TvGuidePojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [TvGuidePojo]
 *
 * Inherit from [PojoMapper].
 */
class TvGuidePojoMapper(): PojoMapper<TvGuide, TvGuidePojo>() {

    /** @see PojoMapper.toPojo */
    override fun TvGuide.toPojo() = with(this ) {
        TvGuidePojo.Impl( id, channelId, programName, programDescription, startTime, endTime )
    }

    /** @see PojoMapper.toEntity */
    override fun TvGuidePojo.toEntity() = with(this ) {
        TvGuide( id, channelId, programName, programDescription, startTime, endTime )
    }
}