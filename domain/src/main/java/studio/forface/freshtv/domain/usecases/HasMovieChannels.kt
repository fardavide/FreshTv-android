package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for get whether any [MovieChannel] is present in [LocalData]
 */
class HasMovieChannels( private val localData: LocalData ) {

    /** @return [Boolean] whether any [MovieChannel] is present in [LocalData] */
    operator fun invoke() = localData.countMovieChannels() > 0
}