package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get the available [ChannelGroup]s of [ChannelGroup.Type.TV]
 */
class GetTvChannelGroups( private val localData: LocalData ) {

    /** @return a [List] of available [ChannelGroup]s of [ChannelGroup.Type.TV] */
    operator fun invoke() = localData.tvGroups()
}