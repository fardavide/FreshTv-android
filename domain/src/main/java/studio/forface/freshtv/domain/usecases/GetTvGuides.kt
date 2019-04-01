package studio.forface.freshtv.domain.usecases

import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalDateTime.*
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get [TvGuide]s for `TvChannel`
 */
class GetTvGuides( private val localData: LocalData ) {

    /** @return all the [TvGuide]s for the given [channelId], included between [from] and [to] */
    operator fun invoke( channelId: String, from: LocalDateTime = MIN, to: LocalDateTime = MAX ) =
        localData.tvGuidesRanged( channelId, from, to )
}