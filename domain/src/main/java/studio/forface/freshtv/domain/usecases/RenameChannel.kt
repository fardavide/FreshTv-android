package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.Repository
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.invoke

/**
 * @author Davide Giuseppe Farella
 * Set a new [IChannel.name]
 */
class RenameChannel( private val repository: Repository) {

    /** Set the given [newName] for the given [IChannel] [channel] */
    operator fun invoke(channel: IChannel, newName: String ) = repository {
        updateChannel( channel.copyObj( name = newName ) )
    }

    /** Set the given [newName] for the [IChannel] with the given [channelId] */
    operator fun invoke( channelId: String, newName: String ) {
        this( repository.channel( channelId ), newName )
    }
}