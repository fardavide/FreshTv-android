package studio.forface.freshtv.localdata.room.sources

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.room.dao.TvChannelDao
import studio.forface.freshtv.localdata.room.entities.TvChannelPojo
import studio.forface.freshtv.localdata.sources.TvChannelsLocalSource

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Channels stored locally
 *
 * Inherit from [TvChannelsLocalSource]
 */
class RoomTvChannelsLocalSource(
    private val dao: TvChannelDao
): TvChannelsLocalSource<TvChannelPojo> {

    /** @return a [List] of all the stored channels [TvChannelPojo] */
    override fun all(): List<TvChannelPojo> = dao.selectAll()

    /** @return the channel [TvChannelPojo] with the given [channelId] */
    override fun channel( channelId: String ): TvChannelPojo = dao.selectById( channelId )

    /** @return a [List] of the stored channels [TvChannelPojo] with the given [groupName] */
    override fun channelsWithGroup( groupName: String ) = dao.selectByGroup( groupName )

    /**
     * @return a [List] of the stored channels [TvChannelPojo] with the given [playlistPath] in
     * [IChannel.playlistPaths]
     */
    override fun channelsWithPlaylist( playlistPath: String ) = dao.selectByPlaylistPath( playlistPath )

    /** @return the [Int] count of the stored channels [TvChannelPojo] */
    override fun count(): Int = dao.count()

    /** Create a new channel [TvChannelPojo] */
    override fun createChannel( channel: TvChannelPojo) {
        dao.insert( channel )
    }

    /** Delete the [TvChannelPojo] with the given [TvChannelPojo.id] */
    override fun delete( channelId: String ) {
        dao.delete( channelId )
    }

    /** Delete all the stored channels [TvChannelPojo] */
    override fun deleteAll() {
        dao.deleteAll()
    }

    /** Update an already stored channel [TvChannelPojo] */
    override fun updateChannel( channel: TvChannelPojo) {
        dao.update( channel )
    }
}