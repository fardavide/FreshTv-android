package studio.forface.freshtv.androiddatabase.sources

import androidx.paging.DataSource
import org.threeten.bp.LocalDateTime

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Channels stored locally, with Paging
 *
 * Inherit from [PagedChannelsLocalSource]
 */
interface PagedTvChannelsLocalSource<ChannelPojo, ChannelWithGuidePojo> : PagedChannelsLocalSource<ChannelPojo> {

    /**
     * @return a [DataSource.Factory] for the stored [ChannelWithGuidePojo]s by the given `Channel` groupName
     * @param time the [LocalDateTime] for query the guides
     */
    fun channelsWithGuideByGroup( groupName: String, time: LocalDateTime ): DataSource.Factory<Int, ChannelWithGuidePojo>
}