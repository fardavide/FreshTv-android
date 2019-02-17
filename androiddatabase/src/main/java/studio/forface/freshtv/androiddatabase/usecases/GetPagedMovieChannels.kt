package studio.forface.freshtv.androiddatabase.usecases

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.domain.entities.MovieChannel

/**
 * @author Davide Giuseppe Farella
 * Get the available [MovieChannel]s with Paging support
 */
class GetPagedMovieChannels internal constructor(private val localData: PagedLocalData ) {

    /**
     * @return a [DataSource] of available [MovieChannel]s
     * @param groupName an OPTIONAL [String] for filter results by [MovieChannel.groupName]
     */
    operator fun invoke( groupName: String? = null ) =
            groupName?.let { localData.movieChannels( groupName ) } ?: localData.movieChannels()
}