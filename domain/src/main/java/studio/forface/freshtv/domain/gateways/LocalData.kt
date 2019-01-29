package studio.forface.freshtv.domain.gateways

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.*
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.domain.utils.or
import studio.forface.freshtv.domain.entities.Playlist

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve and store [IChannel]s and EPG info locally.
 */
interface LocalData {

    /** @return the [IChannel] with the given [channelId] */
    fun channel( channelId: String ): IChannel =
        handle { tvChannel( channelId ) } or { movieChannel( channelId ) }

    /** Delete all the stored [IChannel]s */
    fun deleteAllChannels()

    /** Delete all the stored [TvGuide]s */
    fun deleteAllTvGuides()

    /** @return the [MovieChannel] with the given [channelId] */
    fun movieChannel( channelId: String ): MovieChannel

    /**
     * @return all the [MovieChannel]s from Local Source
     * @param groupName an OPTIONAL filter for [IChannel.groupName]
     */
    fun movieChannels( groupName: String? = null ): List<MovieChannel>

    /** @return the [ChannelGroup] with [ChannelGroup.Type.MOVIE] */
    fun movieGroups() : List<ChannelGroup>

    /** @return all the stored [Playlist]s */
    fun playlists() : List<Playlist>

    /** Store the given [IChannel]s in the appropriate Local Sources */
    fun storeChannels( channels: List<IChannel> )

    /** Store the given [ChannelGroup]s in Local Source */
    fun storeGroup( groups: List<ChannelGroup> )

    /** Store the given [Playlist] in Local Source */
    fun storePlaylists( playlists: List<Playlist> )

    /** Store the given [TvGuide]s in Local Sources */
    fun storeTvGuides( guides: List<TvGuide> )

    /** @return the [TvChannel] with the given [channelId] */
    fun tvChannel( channelId: String ): TvChannel

    /**
     * @return all the [TvChannel]s from Local Source
     * @param groupName an OPTIONAL filter for [IChannel.groupName]
     */
    fun tvChannels( groupName: String? = null ): List<TvChannel>

    /** @return the [ChannelGroup] with [ChannelGroup.Type.TV] */
    fun tvGroups() : List<ChannelGroup>

    /** @return all the [TvGuide]s from Local Source matching the given [channelId], [from] and [to] */
    fun tvGuides( channelId: String, from: LocalDateTime?, to: LocalDateTime? ): List<TvGuide>

    /** Update a [IChannel] in the appropriate Local Source */
    fun updateChannel( channel: IChannel )

    /** Update a [ChannelGroup] in Local Source */
    fun updateGroup( group: ChannelGroup )

    /** Update a [TvGuide] in Local Source */
    fun updateTvGuide( guide: TvGuide )


    /** An enum for results of some [LocalData] operations */
    enum class Result {
        FAILURE, SUCCESS;

        /** @return true if the [Result] is [FAILURE] */
        val isFailed get() = this == FAILURE
    }
}

/**
 * An invoke function for execute a [block] within a [LocalData]
 * @return [T]
 */
operator fun <T> LocalData.invoke(block: LocalData.() -> T ) = block()