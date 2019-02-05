package studio.forface.freshtv.domain.entities

import studio.forface.freshtv.domain.errors.ChannelNotImplementedException
import studio.forface.freshtv.domain.utils.increment
import studio.forface.freshtv.domain.utils.reset

/**
 * @author Davide Giuseppe Farella
 * An interface representing a Channel entity
 */
interface IChannel {

    companion object {
        /** A [String] for [IChannel.groupName] if no group available */
        const val NO_GROUP_NAME = "NoGroup"
    }

    /** The [String] id of the Channel */
    val id: String

    /** The [String] name of the Channel */
    val name: String

    /** A [String] name of the Group */
    val groupName: String

    /** An OPTIONAL [Url] representing the url for an image ( logo ) of the Channel */
    val imageUrl: Url?

    /**
     * A [MutableMap] containing the [String] url for play the stream of the Channel, paired with an [Int] representing
     * the failure count of the url.
     *
     * Every time the url cannot be reached, its failure count must be increased.
     * @see increment
     *
     * Every time the url can be reached successfully, its failure count must be reset.
     * @see reset
     *
     * NOTE: do not use a strict failure limit before blacklisting the failing url, since the user may try many times
     * and may we don't have another url to try with. Use a relaxed limit instead and try first with the url with a
     * lower failure count.
     *
     * NOTE: do not remove the failing url, but blacklist it instead, since a refresh will add it again, also with a
     * failure count set to 0.
     *
     * NOTE: let to the user the power to switch the source url, since maybe the stream quality can defer.
     */
    val mediaUrls: Map<String, Int>

    /**
     * A [MutableList] containing the [String] paths of the associated Playlist's.
     * When a Playlist is removed, we should remove its path from this list, so we can know when a channel must be
     * removed.
     *
     * NOTE: the path will also represents the id of the Playlist, so we can query it for get its name ( if available )
     * or other info.
     */
    val playlistPaths: List<String>

    /** A [Boolean] representing whether the user set the element as favorite */
    val favorite: Boolean

    /** A functions for copy an [IChannel] using the copy method of data class */
    fun copyObj(
        name: String = this.name,
        groupName: String = this.groupName,
        imageUrl: Url? = this.imageUrl,
        mediaUrls: Map<String, Int> = this.mediaUrls,
        playlistPaths: List<String> = this.playlistPaths
    ): IChannel {
        return when( this ) {
            is MovieChannel -> this.copy(
                name = name,
                groupName = groupName,
                imageUrl = imageUrl,
                mediaUrls = mediaUrls,
                playlistPaths = playlistPaths
            )
            is TvChannel -> this.copy(
                name = name,
                groupName = groupName,
                imageUrl = imageUrl,
                mediaUrls = mediaUrls,
                playlistPaths = playlistPaths
            )
            else -> throw ChannelNotImplementedException( IChannel::class, this::copyObj, this::class )
        }
    }

    /** An operator function for merge 2 [IChannel] entities. e.g. `channel1 + channel2` */
    operator fun plus( newChannel: IChannel): IChannel {
        return when( this ) {
            is MovieChannel ->  this + ( newChannel as MovieChannel )
            is TvChannel ->     this + ( newChannel as TvChannel )
            else -> throw ChannelNotImplementedException( IChannel::class, this::plus, this::class )
        }
    }
}