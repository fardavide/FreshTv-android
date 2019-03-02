package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for get whether any [MovieChannel] is present in [LocalData]
 */
class HasMovieChannels( private val localData: LocalData ) {

    /** @return [Boolean] whether any [MovieChannel] is present in [LocalData] */
    operator fun invoke() = localData.countMovieChannels() > 0

    /**
     * @return [ReceiveChannel] of [Boolean] whether any [MovieChannel] is present in [LocalData]
     * Maps the count of [MovieChannel]s into a [Boolean] representing whether any Channels is available
     */
    suspend fun observe() = localData.observeCountMovieChannels().map { it > 0 }
}