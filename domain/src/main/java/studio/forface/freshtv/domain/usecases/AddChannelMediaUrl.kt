package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.Repository
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.invoke
import studio.forface.freshtv.domain.utils.plus

/**
 * @author Davide Giuseppe Farella.
 * Add a media url to [IChannel.mediaUrls]
 */
class AddChannelMediaUrl( private val repository: Repository) {

    /** Add the given [validatedUrl] to the [IChannel.mediaUrls] of the given [IChannel] [channel] */
    operator fun invoke( channel: IChannel, validatedUrl: String ) = repository {
        updateChannel( channel.copyObj( mediaUrls = channel.mediaUrls + validatedUrl ) )
    }

    /** Add the given [validatedUrl] to the [IChannel.mediaUrls] of a [IChannel] with the given [channelId] */
    operator fun invoke( channelId: String, validatedUrl: String ) {
        this( repository.channel( channelId ), validatedUrl )
    }
}