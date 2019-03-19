package studio.forface.freshtv.androiddatabase.usecases

import androidx.paging.DataSource
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.entities.TvChannelWithGuide

/**
 * @author Davide Giuseppe Farella
 * Get the available [TvChannelWithGuide]s with Paging support
 */
class GetPagedTvChannelsWithGuide internal constructor( private val localData: PagedLocalData ) {

    /**
     * @return a [DataSource] of available [TvChannelWithGuide]s
     *
     * @param groupName a [String] for filter results by [TvChannel.groupName]
     *
     * @param time a [LocalDateTime] filter for Channel's Guide
     * Default is [LocalDateTime.now]
     */
    operator fun invoke( groupName: String, time: LocalDateTime = LocalDateTime.now() ) =
            localData.tvChannelsWithGuide( groupName, time )
}