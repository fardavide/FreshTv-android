package studio.forface.freshtv.domain.entities

/**
 * @author Davide Giuseppe Farella.
 * A type of Channel for Movie.
 *
 * Inherit from [IChannel]
 */
data class MovieChannel(
    override val id: String,
    override val name: String,
    override val groupName: String = IChannel.NO_GROUP_NAME,
    override val imageUrl: Url? = null,
    override val mediaUrls: Map<String, Int> = mutableMapOf(),
    override val playlistPaths: List<String> = mutableListOf(),

    /** The OPTIONAL [Int] id of the movie on TheMovieDb.org */
    val tmdbId: Int? = null

): IChannel {

    constructor(
        id: String,
        name: String,
        groupName: String = IChannel.NO_GROUP_NAME,
        imageUrl: Url? = null,
        mediaUrl: String,
        playlistPath: String
    ) : this(
        id =            id,
        name =          name,
        groupName =     groupName,
        imageUrl =      imageUrl,
        mediaUrls =     mutableMapOf( mediaUrl to 0 ),
        playlistPaths = mutableListOf( playlistPath )
    )

    /** An operator function for merge 2 [MovieChannel] entities. e.g. `movieChannel1 + movieChannel2` */
    operator fun plus( newChannel: MovieChannel ) = copy(
        groupName =     groupName,
        imageUrl =      imageUrl?.validOrNull() ?: newChannel.imageUrl,
        mediaUrls =     mediaUrls + newChannel.mediaUrls,
        playlistPaths = playlistPaths + newChannel.playlistPaths,
        tmdbId =        tmdbId ?: newChannel.tmdbId
    )
}