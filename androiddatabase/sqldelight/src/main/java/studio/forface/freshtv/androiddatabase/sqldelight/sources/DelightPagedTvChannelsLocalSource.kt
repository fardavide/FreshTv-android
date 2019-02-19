package studio.forface.freshtv.androiddatabase.sqldelight.sources

import androidx.paging.DataSource
import com.squareup.sqldelight.android.paging.QueryDataSourceFactory
import studio.forface.freshtv.androiddatabase.sources.PagedTvChannelsLocalSource
import studio.forface.freshtv.localdata.sqldelight.TvChannelPojo
import studio.forface.freshtv.localdata.sqldelight.TvChannelQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Channels stored locally, with Paging
 *
 * Inherit from [PagedTvChannelsLocalSource]
 */
class DelightPagedTvChannelsLocalSource(
    private val queries: TvChannelQueries
) : PagedTvChannelsLocalSource<TvChannelPojo> {

    /** @return a [DataSource.Factory] for all the stored channels [TvChannelPojo] */
    override fun all(): DataSource.Factory<Int, TvChannelPojo> = QueryDataSourceFactory(
        queryProvider = queries::selectPaged,
        countQuery = queries.count()
    )

    /** @return a [DataSource.Factory] for the stored channels [TvChannelPojo] with the given `Channel` groupName */
    override fun channelsWithGroup( groupName: String ) = QueryDataSourceFactory(
        queryProvider = { l, o ->  queries.selectPagedByGroup( groupName, l, o ) },
        countQuery = queries.countByGroup( groupName )
    )

    /** @return the stored channels [TvChannelPojo] with the given [playlistPath] in `Channel` playlistPaths */
    override fun channelsWithPlaylist( playlistPath: String ) = QueryDataSourceFactory(
        queryProvider = { l, o ->  queries.selectPagedByPlaylistPath( playlistPath, l, o ) },
        countQuery = queries.countByPlaylistPath( playlistPath )
    )
}