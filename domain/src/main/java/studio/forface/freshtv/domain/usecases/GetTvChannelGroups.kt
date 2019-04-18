package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get the available [ChannelGroup]s of [ChannelGroup.Type.TV]
 */
class GetTvChannelGroups( private val localData: LocalData ) {

    /** @return a [List] of available [ChannelGroup]s of [ChannelGroup.Type.TV] */
    operator fun invoke() = localData.tvGroups()

    /** @return [ReceiveChannel] on a [List] of available [ChannelGroup]s of [ChannelGroup.Type.TV] */
    suspend fun observe() = localData.observeTvGroups()
}