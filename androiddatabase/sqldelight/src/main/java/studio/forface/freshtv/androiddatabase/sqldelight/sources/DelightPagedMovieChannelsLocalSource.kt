package studio.forface.freshtv.androiddatabase.sqldelight.sources

import androidx.paging.DataSource
import com.squareup.sqldelight.android.paging.QueryDataSourceFactory
import studio.forface.freshtv.androiddatabase.sources.PagedMovieChannelsLocalSource
import studio.forface.freshtv.localdata.sqldelight.MovieChannelPojo
import studio.forface.freshtv.localdata.sqldelight.MovieChannelQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Movie Channels stored locally, with Paging
 *
 * Inherit from [PagedMovieChannelsLocalSource]
 */
class DelightPagedMovieChannelsLocalSource(
    private val queries: MovieChannelQueries
) : PagedMovieChannelsLocalSource<MovieChannelPojo> {

    /** @return a [DataSource.Factory] for all the stored channels [MovieChannelPojo] */
    override fun all(): DataSource.Factory<Int, MovieChannelPojo> = QueryDataSourceFactory(
        queryProvider = queries::selectPaged,
        countQuery = queries.count()
    )

    /** @return a [DataSource.Factory] for the stored channels [MovieChannelPojo] with the given `Channel` groupName */
    override fun channelsByGroup( groupName: String ) = QueryDataSourceFactory(
        queryProvider = { l, o ->  queries.selectPagedByGroup( groupName, l, o ) },
        countQuery = queries.countByGroup( groupName )
    )

    /** @return the stored channels [MovieChannelPojo] with the given [playlistPath] in `Channel` playlistPaths */
    override fun channelsByPlaylist( playlistPath: String ) = QueryDataSourceFactory(
        queryProvider = { l, o ->  queries.selectPagedByPlaylistPath( playlistPath, l, o ) },
        countQuery = queries.countByPlaylistPath( playlistPath )
    )
}