package studio.forface.freshtv.localdata.sources

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.MovieChannelPojo
import studio.forface.freshtv.localdata.MovieChannelQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Movie Channels stored locally
 *
 * Inherit from [ChannelsLocalSource]
 */
class MovieChannelsLocalSource( private val queries: MovieChannelQueries):
    ChannelsLocalSource<MovieChannelPojo> {

    /** @return all the stored channels [MovieChannelPojo] */
    override fun all(): List<MovieChannelPojo> = queries.selectAll()
        .executeAsList()

    /** @return the channel [MovieChannelPojo] with the given [channelId] */
    override fun channel( channelId: String ): MovieChannelPojo = queries.selectById( channelId )
        .executeAsOne()

    /** @return the stored channels [MovieChannelPojo] with the given [IChannel.groupName] */
    override fun channelsWithGroup( groupName: String ): List<MovieChannelPojo> =
        queries.selectByGroup( groupName ).executeAsList()

    /** @return the stored channels [MovieChannelPojo] with the given [playlistPath] in [IChannel.playlistPaths] */
    override fun channelsWithPlaylist( playlistPath: String ): List<MovieChannelPojo> =
        queries.selectByPlaylistPath( playlistPath ).executeAsList()

    /** Create a new channel [MovieChannelPojo] */
    override fun createChannel( channel: MovieChannelPojo) = with( channel ) {
        queries.insert(
            id =            id,
            name =          name,
            groupName =     groupName,
            imageUrl =      imageUrl,
            mediaUrls =     mediaUrls,
            playlistPaths = playlistPaths,
            favorite =      favorite,
            tmdbId =        tmdbId
        )
    }

    /** Delete the [MovieChannelPojo] with the given [MovieChannelPojo.id] */
    override fun delete( channelId: String ) {
        queries.delete( channelId )
    }

    /** Delete all the stored channels [MovieChannelPojo] */
    override fun deleteAll() {
        queries.deleteAll()
    }

    /** Update an already stored channel [MovieChannelPojo] */
    override fun updateChannel( channel: MovieChannelPojo) {
        with( channel ) {
            queries.update( id, name, groupName, imageUrl, mediaUrls, playlistPaths, favorite, tmdbId )
        }
    }
}