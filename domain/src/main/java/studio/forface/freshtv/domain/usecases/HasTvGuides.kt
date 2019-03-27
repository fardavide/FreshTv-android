package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * A class for get whether any [TvGuide] is present in [LocalData]
 *
 * @author Davide Giuseppe Farella
 */
class HasTvGuides( private val localData: LocalData ) {

    /** @return [Boolean] whether any [TvGuide] is present in [LocalData] */
    operator fun invoke() = localData.countTvGuides() > 0

    /**
     * @return [ReceiveChannel] of [Boolean] whether any [TvGuide] is present in [LocalData]
     * Maps the count of [TvGuide]s into a [Boolean] representing whether any `Tv Guide` is available
     */
    suspend fun observe() = localData.observeCountTvGuides().map { it > 0 }
}