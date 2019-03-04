package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.updateChannel

/**
 * @author Davide Giuseppe Farella
 * A class for change the [IChannel.favorite] of the given [IChannel]
 */
class UpdateChannelFavoriteState( private val localData: LocalData ) {

    /** Change the [IChannel.favorite] of the [IChannel] with the given [IChannel.id] */
    operator fun invoke( favoritedChannel: FavoritedChannel ) {
        localData.updateChannel( favoritedChannel.id ) { it.copyObj( favorite = favoritedChannel.favorite ) }
    }
}

/** A typealias for a [Pair] of channelId [String] and favorite state to set [Boolean] */
typealias FavoritedChannel = Pair<String, Boolean>

private val FavoritedChannel.id get() = first
private val FavoritedChannel.favorite get() = second
