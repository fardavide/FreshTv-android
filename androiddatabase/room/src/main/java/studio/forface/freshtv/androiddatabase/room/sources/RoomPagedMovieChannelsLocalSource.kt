package studio.forface.freshtv.androiddatabase.room.sources

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.sources.PagedMovieChannelsLocalSource
import studio.forface.freshtv.localdata.room.dao.MovieChannelDao
import studio.forface.freshtv.localdata.room.entities.MovieChannelPojo

/**
 * @author Davide Giuseppe Farella.
 * A source for Movie Channels stored locally, with Paging
 *
 * Inherit from [PagedMovieChannelsLocalSource]
 */
internal class RoomPagedMovieChannelsLocalSource(
    private val dao: MovieChannelDao
): PagedMovieChannelsLocalSource<MovieChannelPojo> {

    /** @return a [DataSource.Factory] for all the stored channels [MovieChannelPojo] */
    override fun all(): DataSource.Factory<Int, MovieChannelPojo> = dao.selectAllPaged()

    /** @return a [DataSource.Factory] for the stored channels [MovieChannelPojo] with the given `Channel`s groupName */
    override fun channelsWithGroup( groupName: String ) = dao.selectByGroupPaged( groupName )

    /** @return the stored channels [MovieChannelPojo] with the given [playlistPath] in `Channel`s playlistPaths */
    override fun channelsWithPlaylist( playlistPath: String ) = dao.selectByPlaylistPathPaged( playlistPath )
}