package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.updateChannel

/**
 * @author Davide Giuseppe Farella
 * A class for change the [IChannel.favorite] of the given [IChannel]
 */
class UpdateChannelFavoriteState( private val localData: LocalData ) {

    /** Change the [IChannel.favorite] of the given [IChannel] */
    operator fun invoke( channel: IChannel, favorite: Boolean ) {
        this( channel.id, favorite )
    }

    /** Change the [IChannel.favorite] of the [IChannel] with the given [channelID] */
    operator fun invoke( channelID: String, favorite: Boolean ) {
        localData.updateChannel( channelID ) { it.copyObj( favorite = favorite ) }
    }
}