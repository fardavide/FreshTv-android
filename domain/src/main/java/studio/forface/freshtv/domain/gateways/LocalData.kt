package studio.forface.freshtv.domain.gateways

import kotlinx.coroutines.channels.ReceiveChannel
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.*
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.domain.utils.or
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve and store [IChannel]s and EPG info locally.
 */
interface LocalData {

    /** @return the [IChannel] with the given [channelId] */
    fun channel( channelId: String ): IChannel =
        handle { tvChannel( channelId ) } or { movieChannel( channelId ) }

    /** @return all the [IChannel] with the given [playlistPath] in [IChannel.playlistPaths] */
    suspend fun channelsWithPlaylist( playlistPath: String ): List<IChannel>

    /** @return the [Int] count of the stored [IChannel] */
    fun countChannels(): Int = countMovieChannels() + countTvChannels()

    /** @return the [Int] count of the stored [MovieChannel]s */
    fun countMovieChannels(): Int

    /** @return [ReceiveChannel] of the [Int] count of the stored [MovieChannel]s */
    suspend fun observeCountMovieChannels(): ReceiveChannel<Int>

    /** @return the [Int] count of the stored [TvChannel]s */
    fun countTvChannels(): Int

    /** @return [ReceiveChannel] of the [Int] count of the stored [TvChannel]s */
    suspend fun observeCountTvChannels(): ReceiveChannel<Int>

    /** Delete all the stored [IChannel]s */
    fun deleteAllChannels()

    /** Delete all the stored [TvGuide]s */
    fun deleteAllTvGuides()

    /** Delete the store [IChannel] with the given [IChannel.id] */
    fun deleteChannel( channelId: String )

    /** Delete the stored [Epg] with the given [Epg.path] */
    fun deleteEpg( epgPath: String )

    /** Delete the stored [Playlist] with the given [Playlist.path] */
    fun deletePlaylist( playlistPath: String )

    /** Delete all the [TvGuide]s from Local Source with [TvGuide.endTime] less that the given [dateTime] */
    fun deleteTvGuidesBefore( dateTime: LocalDateTime )

    /** @return the stored [Epg] with the given [epgPath] */
    fun epg( epgPath: String ): Epg

    /** @return all the stored [Epg]s */
    fun epgs(): List<Epg>

    /** @return the [MovieChannel] with the given [channelId] */
    fun movieChannel( channelId: String ): MovieChannel

    /**
     * @return all the [MovieChannel]s from Local Source
     * @param groupName an OPTIONAL filter for [IChannel.groupName]
     */
    fun movieChannels( groupName: String? = null ): List<MovieChannel>

    /** @return the [ChannelGroup] with [ChannelGroup.Type.MOVIE] */
    fun movieGroups() : List<ChannelGroup>

    /** @return the stored [Playlist] with the given [playlistPath] */
    fun playlist( playlistPath: String ) : Playlist

    /** @return all the stored [Playlist]s */
    fun playlists() : List<Playlist>

    /** Store the given [IChannel] in the appropriate Local Sources */
    fun storeChannel( channel: IChannel )

    /** Store the given [IChannel]s in the appropriate Local Sources */
    fun storeChannels( channels: List<IChannel> ) {
        channels.forEach( ::storeChannel )
    }

    /** Store the given [Epg] in Local Source */
    fun storeEpg( epg: Epg )

    /** Store the given [Epg]s in Local Source */
    fun storeEpgs( epgs: List<Epg> ) {
        epgs.forEach( ::storeEpg )
    }

    /** Store the given [ChannelGroup] in Local Source */
    fun storeGroup( group: ChannelGroup )

    /** Store the given [ChannelGroup]s in Local Source */
    fun storeGroups( groups: List<ChannelGroup> ) {
        groups.forEach( ::storeGroup )
    }

    /** Store the given [Playlist] in Local Source */
    fun storePlaylist( playlist: Playlist )

    /** Store the given [Playlist]s in Local Source */
    fun storePlaylists( playlists: List<Playlist> ) {
        playlists.forEach( ::storePlaylist )
    }

    /** Store the given [TvGuide] in Local Sources */
    fun storeTvGuide( guide: TvGuide )

    /** Store the given [TvGuide]s in Local Sources */
    fun storeTvGuides( guides: List<TvGuide> ) {
        guides.forEach( ::storeTvGuide )
    }

    /** @return the [TvChannel] with the given [channelId] */
    fun tvChannel( channelId: String ): TvChannel

    /**
     * @return all the [TvChannel]s from Local Source
     * @param groupName an OPTIONAL filter for [IChannel.groupName]
     */
    fun tvChannels( groupName: String? = null ): List<TvChannel>

    /** @return the [ChannelGroup] with [ChannelGroup.Type.TV] */
    fun tvGroups() : List<ChannelGroup>

    /** @return all the [TvGuide]s from Local Source matching the given [channelId] and [time] */
    fun tvGuides( channelId: String, time: LocalDateTime? = null ): List<TvGuide>

    /** @return all the [TvGuide]s from Local Source matching the given [channelId], [from] and [to] */
    fun tvGuidesRanged( channelId: String, from: LocalDateTime?, to: LocalDateTime? ): List<TvGuide>

    /** Update a [IChannel] in the appropriate Local Source */
    fun updateChannel( channel: IChannel )

    /** Update an [Epg] in the appropriate Local Source */
    fun updateEpg( epg: Epg )

    /** Update a [ChannelGroup] in Local Source */
    fun updateGroup( group: ChannelGroup )

    /** Update a [Playlist] in Local Source */
    fun updatePlaylist( playlist: Playlist )

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
operator fun <T> LocalData.invoke( block: LocalData.() -> T ) = block()

/** Update a [IChannel] in the appropriate Local Source, by its [IChannel.id] and a lambda [block] */
inline fun LocalData.updateChannel( channelId: String, block: (IChannel) -> IChannel ) {
    updateChannel( block( channel( channelId ) ) )
}

/**
 * Update a [Epg] in the appropriate Local Source, by its [Epg.path] and a lambda [block]
 * If the new [Epg.path] is different from the old [Epg.path], just [LocalData.updateEpg]
 * else, [LocalData.deleteEpg] the old [Epg] and [LocalData.storeEpg] with the new [Epg]
 */
inline fun LocalData.updateEpg( epgPath: String, block: (Epg) -> Epg ) {
    val oldEpg = epg( epgPath )
    val newEpg = block( oldEpg )
    if ( oldEpg.path == newEpg.path )
        updateEpg( newEpg )
    else {
        deleteEpg( oldEpg.path )
        storeEpg( newEpg )
    }
}

/**
 * Update a [Playlist] in the appropriate Local Source, by its [Playlist.path] and a lambda [block]
 * If the new [Playlist.path] is different from the old [Playlist.path], just [LocalData.updatePlaylist]
 * else, [LocalData.deletePlaylist] the old [Playlist] and [LocalData.storePlaylist] with the new [Playlist]
 */
inline fun LocalData.updatePlaylist( playlistPath: String, block: (Playlist) -> Playlist ) {
    val oldPlaylist = playlist( playlistPath )
    val newPlaylist = block( oldPlaylist )
    if ( oldPlaylist.path == newPlaylist.path )
        updatePlaylist( newPlaylist )
    else {
        deletePlaylist( oldPlaylist.path )
        storePlaylist( newPlaylist )
    }
}