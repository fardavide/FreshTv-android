package studio.forface.freshtv.localdata.sources

import studio.forface.freshtv.domain.entities.IChannel

/**
 * @author Davide Giuseppe Farella.
 * A source for [IChannel]s stored locally
 */
interface ChannelsLocalSource<T> {

    /** @return all the stored channels [T] */
    fun all(): List<T>

    /** @return the channel [T] with the given [channelId] */
    fun channel( channelId: String ): T

    /** @return the stored channels [T] with the given [IChannel.groupName] */
    fun channelsWithGroup( groupName: String ): List<T>

    /** @return the stored channels [T] with the given [playlistPath] in [IChannel.playlistPaths] */
    fun channelsWithPlaylist( playlistPath: String ): List<T>

    /** Create a new channel [T] */
    fun createChannel( channel: T )

    /** Delete the [IChannel] with the given [IChannel.id] */
    fun delete( channelId: String )

    /** Delete all the stored channels [T] */
    fun deleteAll()

    /** Update an already stored channel [T] */
    fun updateChannel( channel: T )
}