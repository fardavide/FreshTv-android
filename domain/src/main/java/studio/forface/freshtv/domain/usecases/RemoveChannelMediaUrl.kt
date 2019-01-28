package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.Repository
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.invoke

/**
 * @author Davide Giuseppe Farella.
 * Remove a [IChannel.mediaUrls]
 */
class RemoveChannelMediaUrl( private val repository: Repository) {

    /** Remove the given [mediaUrl] from the given [IChannel] [channel] */
    operator fun invoke(channel: IChannel, mediaUrl: String ) = repository {
        updateChannel( channel.copyObj( mediaUrls = channel.mediaUrls - mediaUrl ) )
    }

    /** Remove the given [mediaUrl] from a [IChannel] with the given [channelId] */
    operator fun invoke( channelId: String, mediaUrl: String ) {
        this( repository.channel( channelId ), mediaUrl )
    }
}