package studio.forface.freshtv.androiddatabase.sources

import androidx.paging.DataSource
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.IChannel

/**
 * @author Davide Giuseppe Farella.
 * A source for [IChannel]s stored locally, with Paging
 */
interface PagedChannelsLocalSource<Channel> {

    /** @return a [DataSource.Factory] for all the stored [Channel]s */
    fun all(): DataSource.Factory<Int, Channel>

    /** @return a [DataSource.Factory] for the stored [Channel]s by the given [IChannel.groupName] */
    fun channelsByGroup( groupName: String ): DataSource.Factory<Int, Channel>

    /** @return the stored [Channel]s by the given [playlistPath] in [IChannel.playlistPaths] */
    fun channelsByPlaylist( playlistPath: String ): DataSource.Factory<Int, Channel>
}