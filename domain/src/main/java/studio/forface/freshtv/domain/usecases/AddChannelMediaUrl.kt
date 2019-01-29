package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.gateways.invoke
import studio.forface.freshtv.domain.utils.plus

/**
 * @author Davide Giuseppe Farella.
 * Add a media url to [IChannel.mediaUrls]
 */
class AddChannelMediaUrl( private val localData: LocalData) {

    /** Add the given [validatedUrl] to the [IChannel.mediaUrls] of the given [IChannel] [channel] */
    operator fun invoke( channel: IChannel, validatedUrl: String ) = localData {
        updateChannel( channel.copyObj( mediaUrls = channel.mediaUrls + validatedUrl ) )
    }

    /** Add the given [validatedUrl] to the [IChannel.mediaUrls] of a [IChannel] with the given [channelId] */
    operator fun invoke( channelId: String, validatedUrl: String ) {
        this( localData.channel( channelId ), validatedUrl )
    }
}