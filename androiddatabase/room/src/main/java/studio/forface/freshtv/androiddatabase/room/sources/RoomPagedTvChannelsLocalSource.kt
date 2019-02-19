package studio.forface.freshtv.androiddatabase.room.sources

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.sources.PagedTvChannelsLocalSource
import studio.forface.freshtv.localdata.room.dao.TvChannelDao
import studio.forface.freshtv.localdata.room.entities.TvChannelPojo

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Channels stored locally, with Paging
 *
 * Inherit from [PagedTvChannelsLocalSource]
 */
internal class RoomPagedTvChannelsLocalSource(
    private val dao: TvChannelDao
) : PagedTvChannelsLocalSource<TvChannelPojo> {

    /** @return a [DataSource.Factory] for all the stored channels [TvChannelPojo] */
    override fun all(): DataSource.Factory<Int, TvChannelPojo> = dao.selectAllPaged()

    /** @return a [DataSource.Factory] for the stored channels [TvChannelPojo] with the given `Channel`s groupName */
    override fun channelsWithGroup( groupName: String ) = dao.selectByGroupPaged( groupName )

    /** @return the stored channels [TvChannelPojo] with the given [playlistPath] in `Channel`s playlistPaths */
    override fun channelsWithPlaylist( playlistPath: String ) = dao.selectByPlaylistPathPaged( playlistPath )
}