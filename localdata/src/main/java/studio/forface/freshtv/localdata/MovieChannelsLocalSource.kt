package studio.forface.freshtv.localdata

/**
 * @author Davide Giuseppe Farella.
 * A source for Movie Channels stored locally
 *
 * Inherit from [ChannelsLocalSource]
 */
class MovieChannelsLocalSource( private val queries: MovieChannelQueries ): ChannelsLocalSource<MovieChannelPojo> {

    /** @return all the stored channels [MovieChannelPojo] */
    override fun all(): List<MovieChannelPojo> = queries.selectAll()
        .executeAsList()

    /** @return the channel [MovieChannelPojo] with the given [channelId] */
    override fun channel( channelId: String ): MovieChannelPojo = queries.selectById( channelId )
        .executeAsOne()

    /** @return the stored channels [MovieChannelPojo] with the given [groupName] */
    override fun channels( groupName: String ): List<MovieChannelPojo> =
        queries.selectByGroup( groupName ).executeAsList()

    /** Create a new channel [MovieChannelPojo] */
    override fun createChannel( channel: MovieChannelPojo ) = with( channel ) {
        queries.insert(
            id =            id,
            name =          name,
            groupName =     groupName,
            imageUrl =      imageUrl,
            mediaUrls =     mediaUrls,
            playlistPaths = playlistPaths,
            tmdbId =        tmdbId
        )
    }

    /** Delete all the stored channels [MovieChannelPojo] */
    override fun deleteAll() {
        queries.deleteAll()
    }

    /** Update an already stored channel [MovieChannelPojo] */
    override fun updateChannel( channel: MovieChannelPojo ) {
        with( channel ) {
            queries.update( id, name, groupName, imageUrl, mediaUrls, playlistPaths, tmdbId )
        }
    }
}