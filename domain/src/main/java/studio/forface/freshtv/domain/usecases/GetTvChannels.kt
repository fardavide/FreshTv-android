package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get the available [TvChannel]s
 */
class GetTvChannels( private val localData: LocalData ) {

    /**
     * @return a [List] of available [TvChannel]s
     * @param groupName an OPTIONAL [String] for filter results by [TvChannel.groupName]
     */
    operator fun invoke( groupName: String? = null ) = localData.tvChannels( groupName )
}