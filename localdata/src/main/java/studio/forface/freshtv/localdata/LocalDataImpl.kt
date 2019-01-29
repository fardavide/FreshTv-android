package studio.forface.freshtv.localdata

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.*
import studio.forface.freshtv.domain.exceptions.ChannelNotImplementedError
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.LocalData.Result
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.localdata.mappers.*

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve and store [IChannel]s and EPG info locally.
 */
private class LocalDataImpl(
    private val channelGroups: ChannelGroupsLocalSource,
    private val movieChannels: MovieChannelsLocalSource,
    private val playlists: PlaylistsLocalSource,
    private val tvChannels: TvChannelsLocalSource,
    private val tvGuides: TvGuidesLocalSource,
    private val channelGroupMapper: ChannelGroupPojoMapper,
    private val movieChannelMapper: MovieChannelPojoMapper,
    private val playlistMapper: PlaylistPojoMapper,
    private val tvChannelMapper: TvChannelPojoMapper,
    private val tvGuideMapper: TvGuidePojoMapper
) : LocalData {

    /** Store a [IChannel] in the appropriate [ChannelsLocalSource] */
    private fun createChannel( channel: IChannel) {
        when( channel ) {
            is MovieChannel -> movieChannels.createChannel( movieChannelMapper { channel.toPojo() } )
            is TvChannel -> tvChannels.createChannel( tvChannelMapper { channel.toPojo() } )
            else -> throw ChannelNotImplementedError( this::class, this::storeChannels, channel::class )
        }
    }

    /** Store a [ChannelGroup] in [ChannelGroupsLocalSource] */
    private fun createGroup( group: ChannelGroup) {
        channelGroups.createChannelGroup( channelGroupMapper { group.toPojo() } )
    }

    /** Store a [Playlist] in [PlaylistsLocalSource] */
    private fun createPlaylist( playlist: Playlist) {
        playlists.createPlaylist( playlistMapper { playlist.toPojo() } )
    }

    /** Store a [TvGuide] in [TvGuidesLocalSource] */
    private fun createTvGuide( guide: TvGuide) {
        tvGuides.createGuide( tvGuideMapper { guide.toPojo() } )
    }

    /** Delete all the stored [IChannel]s */
    override fun deleteAllChannels() {
        movieChannels.deleteAll()
        tvChannels.deleteAll()
    }

    /** Delete all the stored [TvGuide]s */
    override fun deleteAllTvGuides() {
        tvGuides.deleteAll()
    }

    /**
     * Merge the given [newChannel] with the already existing [IChannel] with the same [IChannel.id]
     * @return [Result.SUCCESS] if the operation is succeed, else [Result.FAILURE] if some exception occurs while
     * retrieving the old [IChannel]
     */
    private fun mergeChannel( newChannel: IChannel ): Result {
        val oldChannel = when( newChannel ) {
            is MovieChannel -> handle { movieChannelMapper { movieChannels.channel( newChannel.id ).toEntity() } }
            is TvChannel -> handle { tvChannelMapper { tvChannels.channel( newChannel.id ).toEntity() } }
            else -> throw ChannelNotImplementedError( this::class, this::storeChannels, newChannel::class )
        }
        oldChannel ?: return Result.FAILURE
        updateChannel(oldChannel + newChannel )
        return Result.SUCCESS
    }

    /**
     * Merge the given [newGuide] with the already existing [TvGuide] with the same [TvGuide.id]
     * @return [Result.SUCCESS] if the operation is succeed, else [Result.FAILURE] if some exception occurs while
     * retrieving the old [TvGuide]
     */
    private fun mergeGuide( newGuide: TvGuide ): Result {
        val oldGuide = handle { tvGuideMapper { tvGuides.guide( newGuide.id ).toEntity() } }
        oldGuide ?: return Result.FAILURE
        updateTvGuide( oldGuide + newGuide )
        return Result.SUCCESS
    }

    /**
     * Merge the given [newGroup] with the already existing [ChannelGroup] with the same [ChannelGroup.name] and
     * [ChannelGroup.type]
     * @return [Result.SUCCESS] if the operation is succeed, else [Result.FAILURE] if some exception occurs while
     * retrieving the old [ChannelGroup]
     */
    private fun mergeGroup( newGroup: ChannelGroup ): Result {
        val oldGroup = handle { channelGroupMapper { channelGroups.group( newGroup.id ).toEntity() } }
        oldGroup ?: return Result.FAILURE
        updateGroup( oldGroup + newGroup )
        return Result.SUCCESS
    }

    /** @return the [MovieChannel] with the given [channelId] */
    override fun movieChannel( channelId: String ) =
        movieChannelMapper{ movieChannels.channel( channelId ).toEntity() }

    /**
     * @return all the [MovieChannel]s from [MovieChannelsLocalSource]
     * @param groupName an OPTIONAL filter for [IChannel.groupName]
     */
    override fun movieChannels( groupName: String? ): List<MovieChannel> {
        val pojos = groupName?.let { movieChannels.channels( it ) } ?: movieChannels.all()
        return pojos.map { movieChannelMapper { it.toEntity() } }
    }

    /** @return the [ChannelGroup] with [ChannelGroup.Type.MOVIE] */
    override fun movieGroups(): List<ChannelGroup> =
        channelGroups.allMovie().map { channelGroupMapper { it.toEntity() } }

    /** @return all the stored [Playlist]s */
    override fun playlists(): List<Playlist> =
        playlists.all().map { playlistMapper { it.toEntity() } }

    /** Store the given [IChannel]s in [ChannelsLocalSource]s */
    override fun storeChannels( channels: List<IChannel> ) {
        channels.forEach { if ( mergeChannel( it ).isFailed ) createChannel( it ) }
    }

    /** Store the given [ChannelGroup] in [ChannelGroupsLocalSource] */
    override fun storeGroup( groups: List<ChannelGroup> ) {
        groups.forEach { if ( mergeGroup( it ).isFailed ) createGroup( it ) }
    }

    /** Store the given [Playlist] in [PlaylistsLocalSource] */
    override fun storePlaylists( playlists: List<Playlist> ) {
        playlists.forEach { createPlaylist( it ) }
    }

    /** Store the given [TvGuide]s in [TvGuidesLocalSource]s */
    override fun storeTvGuides( guides: List<TvGuide> ) {
        guides.forEach { if ( mergeGuide( it ).isFailed ) createTvGuide( it ) }
    }

    /** @return the [TvChannel] with the given [channelId] */
    override fun tvChannel( channelId: String ) =
        tvChannelMapper { tvChannels.channel( channelId ).toEntity() }

    /**
     * @return all the [TvChannel]s from [TvChannelsLocalSource]
     * @param groupName an OPTIONAL filter for [IChannel.groupName]
     */
    override fun tvChannels( groupName: String? ): List<TvChannel> {
        val pojos = groupName?.let { tvChannels.channels( it ) } ?: tvChannels.all()
        return pojos.map { tvChannelMapper { it.toEntity() } }
    }

    /** @return the [ChannelGroup] with [ChannelGroup.Type.TV] */
    override fun tvGroups(): List<ChannelGroup> =
            channelGroups.allTv().map { channelGroupMapper { it.toEntity() } }

    /**
     * @return all the [TvGuide]s from [TvGuidesLocalSource] matching the given [channelId], [from] and [to]
     * @see TvGuidesLocalSource.guidesForChannel for parameters details.
     */
    override fun tvGuides( channelId: String, from: LocalDateTime?, to: LocalDateTime? ): List<TvGuide> =
            tvGuides.guidesForChannel( channelId, from, to ).map { tvGuideMapper { it.toEntity() } }

    /** Update a [IChannel] in the appropriate [ChannelsLocalSource] */
    override fun updateChannel( channel: IChannel ) {
        when ( channel ) {
            is MovieChannel -> movieChannels.updateChannel( movieChannelMapper { channel.toPojo() } )
            is TvChannel -> tvChannels.updateChannel( tvChannelMapper { channel.toPojo() } )
            else -> throw ChannelNotImplementedError( this::class, this::storeChannels, channel::class )
        }
    }

    /** Update a [ChannelGroup] in [ChannelGroupsLocalSource] */
    override fun updateGroup( group: ChannelGroup ) {
        channelGroups.updateGroup( channelGroupMapper { group.toPojo() } )
    }

    /** Update a [TvGuide] in [TvGuidesLocalSource] */
    override fun updateTvGuide( guide: TvGuide ) {
        tvGuides.updateGuide( tvGuideMapper { guide.toPojo() } )
    }
}

/**
 * A constructor-function for [LocalDataImpl]
 * @return [LocalData]
 */
@Suppress("FunctionName")
fun Repository(
    channelGroups: ChannelGroupsLocalSource,
    movieChannels: MovieChannelsLocalSource,
    playlists: PlaylistsLocalSource,
    tvChannels: TvChannelsLocalSource,
    tvGuides: TvGuidesLocalSource,
    channelGroupMapper: ChannelGroupPojoMapper,
    movieChannelMapper: MovieChannelPojoMapper,
    playlistMapper: PlaylistPojoMapper,
    tvChannelMapper: TvChannelPojoMapper,
    tvGuideMapper: TvGuidePojoMapper
): LocalData = LocalDataImpl(
    channelGroups,
    movieChannels,
    playlists,
    tvChannels,
    tvGuides,
    channelGroupMapper,
    movieChannelMapper,
    playlistMapper,
    tvChannelMapper,
    tvGuideMapper
)