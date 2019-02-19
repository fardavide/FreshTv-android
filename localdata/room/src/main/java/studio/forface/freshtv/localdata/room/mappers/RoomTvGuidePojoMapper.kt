package studio.forface.freshtv.localdata.room.mappers

import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.localdata.room.entities.TvGuidePojo
import studio.forface.freshtv.localdata.mappers.PojoMapper
import studio.forface.freshtv.localdata.mappers.TvGuidePojoMapper

/**
 * @author Davide Giuseppe Farella.
 * A Mapper for [TvGuidePojo]
 *
 * Inherit from [TvGuidePojoMapper] and [PojoMapper].
 */
class RoomTvGuidePojoMapper : TvGuidePojoMapper<TvGuidePojo> {

    /** @see PojoMapper.toPojo */
    override fun TvGuide.toPojo() = with(this ) {
        val pojoCredits = credits?.let{ TvGuidePojo.Credits( it.director, it.actors ) }
        TvGuidePojo(
                id =                id,
                channelId =         channelId,
                title =             title,
                description =       description,
                imageUrl =          imageUrl,
                category =          category,
                year =              year,
                country =           country,
                credits =           pojoCredits,
                rating =            rating,
                startTime =         startTime,
                endTime =           endTime
        )
    }

    /** @see PojoMapper.toEntity */
    override fun TvGuidePojo.toEntity() = with(this ) {
        val entityCredits = credits?.let{ TvGuide.Credits( it.director, it.actors ) }

        TvGuide(
                id =            id,
                channelId =     channelId,
                title =         title,
                description =   description,
                imageUrl =      imageUrl,
                category =      category,
                year =          year,
                country =       country,
                credits =       entityCredits,
                rating =        rating,
                startTime =     startTime,
                endTime =       endTime
        )
    }
}