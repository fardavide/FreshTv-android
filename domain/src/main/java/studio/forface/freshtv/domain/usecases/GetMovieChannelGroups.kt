package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get the available [ChannelGroup]s of [ChannelGroup.Type.MOVIE]
 */
class GetMovieChannelGroups( private val localData: LocalData ) {

    /** @return a [List] of available [ChannelGroup]s of [ChannelGroup.Type.MOVIE] */
    operator fun invoke() = localData.movieGroups()

    /** @return [ReceiveChannel] on a [List] of available [ChannelGroup]s of [ChannelGroup.Type.MOVIE] */
    suspend fun observe() = localData.observeMovieGroups()
}