package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.localdata.TvGuidePojo

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [TvGuidePojo]
 *
 * Inherit from [PojoMapper].
 */
class TvGuidePojoMapper : PojoMapper<TvGuide, TvGuidePojo>() {

    /** @see PojoMapper.toPojo */
    override fun TvGuide.toPojo() = with(this ) {
        TvGuidePojo.Impl(
                id =                id,
                channelId =         channelId,
                title =             title,
                description =       description,
                imageUrl =          imageUrl,
                category =          category,
                year =              year,
                country =           country,
                credits_director =  credits?.director,
                credits_actors =     credits?.actors,
                rating =            rating,
                startTime =         startTime,
                endTime =           endTime
        )
    }

    /** @see PojoMapper.toEntity */
    override fun TvGuidePojo.toEntity() = with(this ) {
        val credits = if ( credits_director != null && credits_actors != null )
            TvGuide.Credits( credits_director!!, credits_actors!! ) else null

        TvGuide(
                id =            id,
                channelId =     channelId,
                title =         title,
                description =   description,
                imageUrl =      imageUrl,
                category =      category,
                year =          year,
                country =       country,
                credits =       credits,
                rating =        rating,
                startTime =     startTime,
                endTime =       endTime
        )
    }
}