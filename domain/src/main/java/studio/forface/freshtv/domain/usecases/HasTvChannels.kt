package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for get whether any [TvChannel] is present in [LocalData]
 */
class HasTvChannels( private val localData: LocalData ) {

    /** @return [Boolean] whether any [TvChannel] is present in [LocalData] */
    operator fun invoke() = localData.countTvChannels() > 0

    /**
     * @return [ReceiveChannel] of [Boolean] whether any [TvChannel] is present in [LocalData]
     * Maps the count of [TvChannel]s into a [Boolean] representing whether any Channels is available
     */
    suspend fun observe() = localData.observeCountTvChannels().map { it > 0 }
}