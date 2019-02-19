package studio.forface.freshtv.androiddatabase.sources

import androidx.paging.DataSource
import studio.forface.freshtv.domain.entities.IChannel

/**
 * @author Davide Giuseppe Farella.
 * A source for [IChannel]s stored locally, with Paging
 */
interface PagedChannelsLocalSource<T> {

    /** @return a [DataSource.Factory] for all the stored channels [T] */
    fun all(): DataSource.Factory<Int, T>

    /** @return a [DataSource.Factory] for the stored channels [T] with the given [IChannel.groupName] */
    fun channelsWithGroup( groupName: String ): DataSource.Factory<Int, T>

    /** @return the stored channels [T] with the given [playlistPath] in [IChannel.playlistPaths] */
    fun channelsWithPlaylist( playlistPath: String ): DataSource.Factory<Int, T>
}