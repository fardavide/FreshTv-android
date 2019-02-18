package studio.forface.freshtv.localdata.room.sources

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.room.dao.MovieChannelDao
import studio.forface.freshtv.localdata.room.entities.MovieChannelPojo
import studio.forface.freshtv.localdata.sources.MovieChannelsLocalSource

/**
 * @author Davide Giuseppe Farella.
 * A source for Movie Channels stored locally
 *
 * Inherit from [MovieChannelsLocalSource]
 */
class RoomMovieChannelsLocalSource(
    private val dao: MovieChannelDao
): MovieChannelsLocalSource<MovieChannelPojo> {

    /** @return all the stored channels [MovieChannelPojo] */
    override fun all(): List<MovieChannelPojo> = dao.selectAll()

    /** @return the channel [MovieChannelPojo] with the given [channelId] */
    override fun channel( channelId: String ): MovieChannelPojo = dao.selectById( channelId )

    /** @return a [List] of the stored channels [MovieChannelPojo] with the given [IChannel.groupName] */
    override fun channelsWithGroup( groupName: String ): List<MovieChannelPojo> = dao.selectByGroup( groupName )

    /**
     * @return a [List] of the stored channels [MovieChannelPojo] with the given [playlistPath] in
     * [IChannel.playlistPaths]
     */
    override fun channelsWithPlaylist( playlistPath: String ) = dao.selectByPlaylistPath( playlistPath )

    /** @return the [Int] count of the stored channels [MovieChannelPojo] */
    override fun count(): Int = dao.count()

    /** Create a new channel [MovieChannelPojo] */
    override fun createChannel( channel: MovieChannelPojo ) {
        dao.insert( channel )
    }

    /** Delete the [MovieChannelPojo] with the given [MovieChannelPojo.id] */
    override fun delete( channelId: String ) {
        dao.delete( channelId )
    }

    /** Delete all the stored channels [MovieChannelPojo] */
    override fun deleteAll() {
        dao.deleteAll()
    }

    /** Update an already stored channel [MovieChannelPojo] */
    override fun updateChannel( channel: MovieChannelPojo) {
        dao.update( channel )
    }
}