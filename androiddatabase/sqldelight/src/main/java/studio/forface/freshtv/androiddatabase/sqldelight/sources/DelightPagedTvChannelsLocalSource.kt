package studio.forface.freshtv.androiddatabase.sqldelight.sources

import androidx.paging.DataSource
import com.squareup.sqldelight.android.paging.QueryDataSourceFactory
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.androiddatabase.sources.PagedTvChannelsLocalSource
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.sqldelight.SelectPagedByGroupWithGuide
import studio.forface.freshtv.localdata.sqldelight.TvChannelPojo
import studio.forface.freshtv.localdata.sqldelight.TvChannelQueries
import studio.forface.freshtv.localdata.sqldelight.utils.TvChannelWithGuidePojo

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Channels stored locally, with Paging
 *
 * Inherit from [PagedTvChannelsLocalSource]
 */
class DelightPagedTvChannelsLocalSource(
    private val queries: TvChannelQueries
) : PagedTvChannelsLocalSource<TvChannelPojo, TvChannelWithGuidePojo> {

    /** @return a [DataSource.Factory] for all the stored channels [TvChannelPojo] */
    override fun all(): DataSource.Factory<Int, TvChannelPojo> = QueryDataSourceFactory(
        queryProvider = queries::selectPaged,
        countQuery = queries.count()
    )

    /** @return a [DataSource.Factory] for the stored channels [TvChannelPojo] with the given `Channel` groupName */
    override fun channelsByGroup( groupName: String ) = QueryDataSourceFactory(
        queryProvider = { l, o ->  queries.selectPagedByGroup( groupName, l, o ) },
        countQuery = queries.countByGroup( groupName )
    )

    /** @return the stored channels [TvChannelPojo] with the given [playlistPath] in `Channel` playlistPaths */
    override fun channelsByPlaylist( playlistPath: String ) = QueryDataSourceFactory(
        queryProvider = { l, o ->  queries.selectPagedByPlaylistPath( playlistPath, l, o ) },
        countQuery = queries.countByPlaylistPath( playlistPath )
    )

    /**
     * @return a [DataSource.Factory] for the stored [TvChannelWithGuidePojo]s by the given `Channel` groupName
     * @param time the [LocalDateTime] for query the guides
     */
    override fun channelsWithGuideByGroup( groupName: String, time: LocalDateTime ) = QueryDataSourceFactory(
        queryProvider = { l, o -> queries.selectPagedByGroupWithGuide( time, groupName, l, o ) },
        countQuery = queries.countByGroup( groupName )
    )
}