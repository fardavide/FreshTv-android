package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.utils.invoke

/**
 * @author Davide Giuseppe Farella
 * Set a new [IChannel.name]
 */
class RenameChannel( private val localData: LocalData) {

    /** Set the given [newName] for the given [IChannel] [channel] */
    operator fun invoke(channel: IChannel, newName: String ) = localData {
        updateChannel( channel.copyObj( name = newName ) )
    }

    /** Set the given [newName] for the [IChannel] with the given [channelId] */
    operator fun invoke( channelId: String, newName: String ) {
        this( localData.channel( channelId ), newName )
    }
}