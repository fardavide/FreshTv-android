package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get the available [MovieChannel]s
 */
class GetMovieChannels( private val localData: LocalData ) {

    /**
     * @return a [List] of available [MovieChannel]s
     * @param groupName an OPTIONAL [String] for filter results by [MovieChannel.groupName]
     */
    operator fun invoke( groupName: String? = null ) = localData.movieChannels( groupName )
}