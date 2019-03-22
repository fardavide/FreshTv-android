package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get an [IChannel]
 */
class GetChannel( private val localData: LocalData ) {

    /**
     * @return a [IChannel] with the given [channelId]
     * @param channelId the [IChannel.id]
     */
    operator fun invoke( channelId: String ) = localData.channel( channelId )

    /**
     * @return [ReceiveChannel] of [IChannel] with the given [channelId]
     * @param channelId the [IChannel.id]
     */
    suspend fun observe( channelId: String ) = localData.observeChannel( channelId )
}