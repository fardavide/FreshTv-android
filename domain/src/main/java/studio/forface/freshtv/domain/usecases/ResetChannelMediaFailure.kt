package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.utils.invoke
import studio.forface.freshtv.domain.utils.reset

/**
 * @author Davide Giuseppe Farella.
 * Reset the failure count of a media url in [IChannel.mediaUrls]
 */
class ResetChannelMediaFailure( private val localData: LocalData) {

    /** Reset the failure count of the given [mediaUrl] in given [IChannel] channel */
    operator fun invoke( channel: IChannel, mediaUrl: String ) = localData {
        updateChannel( channel.copyObj( mediaUrls = channel.mediaUrls reset mediaUrl ) )
    }

    /** Reset the failure count of the given [mediaUrl] in the [IChannel] with the given [channelId] */
    operator fun invoke( channelId: String, mediaUrl: String ) {
        this( localData.channel( channelId ), mediaUrl )
    }
}