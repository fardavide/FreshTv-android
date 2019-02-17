package studio.forface.freshtv.androiddatabase.usecases

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.domain.entities.TvChannel

/**
 * @author Davide Giuseppe Farella
 * Get the available [TvChannel]s with Paging support
 */
class GetPagedTvChannels internal constructor( private val localData: PagedLocalData ) {

    /**
     * @return a [DataSource] of available [TvChannel]s
     * @param groupName an OPTIONAL [String] for filter results by [TvChannel.groupName]
     */
    operator fun invoke( groupName: String? = null ) =
            groupName?.let { localData.tvChannels( groupName ) } ?: localData.tvChannels()
}