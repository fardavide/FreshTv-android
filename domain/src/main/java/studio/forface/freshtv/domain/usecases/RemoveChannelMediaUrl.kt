package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.utils.invoke

/**
 * @author Davide Giuseppe Farella.
 * Remove a [IChannel.mediaUrls]
 */
class RemoveChannelMediaUrl( private val localData: LocalData) {

    /** Remove the given [mediaUrl] from the given [IChannel] [channel] */
    operator fun invoke(channel: IChannel, mediaUrl: String ) = localData {
        updateChannel( channel.copyObj( mediaUrls = channel.mediaUrls - mediaUrl ) )
    }

    /** Remove the given [mediaUrl] from a [IChannel] with the given [channelId] */
    operator fun invoke( channelId: String, mediaUrl: String ) {
        this( localData.channel( channelId ), mediaUrl )
    }
}