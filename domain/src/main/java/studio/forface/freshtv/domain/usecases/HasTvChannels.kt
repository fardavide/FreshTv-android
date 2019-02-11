package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for get whether any [TvChannel] is present in [LocalData]
 */
class HasTvChannels( private val localData: LocalData ) {

    /** @return [Boolean] whether any [TvChannel] is present in [LocalData] */
    operator fun invoke() = localData.countTvChannels() > 0
}