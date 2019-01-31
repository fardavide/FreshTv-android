package studio.forface.freshtv.localdata

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.*
import studio.forface.freshtv.domain.errors.ChannelNotImplementedException
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.LocalData.Result
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.localdata.mappers.*
import studio.forface.freshtv.localdata.sources.*

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve and store [IChannel]s and EPG info locally.
 */
internal class LocalDataImpl(
    private val channelGroups: ChannelGroupsLocalSource,
    private val movieChannels: MovieChannelsLocalSource,
    private val playlists: PlaylistsLocalSource,
    private val tvChannels: TvChannelsLocalSource,
    private val tvGuides: TvGuidesLocalSource,
    private val channelGroupMapper: ChannelGroupPojoMapper = ChannelGroupPojoMapper(),
    private val movieChannelMapper: MovieChannelPojoMapper = MovieChannelPojoMapper(),
    private val playlistMapper: PlaylistPojoMapper = PlaylistPojoMapper(),
    private val tvChannelMapper: TvChannelPojoMapper = TvChannelPojoMapper(),
    private val tvGuideMapper: TvGuidePojoMapper = TvGuidePojoMapper()
) : LocalData {

    /** @return all the [IChannel] with the given [playlistPath] in [IChannel.playlistPaths] */
    override suspend fun channelsWithPlaylist( playlistPath: String ): List<IChannel> = coroutineScope {
        val movies = async {
            movieChannels.channelsWithPlaylist( playlistPath ).map { movieChannelMapper { it.toEntity() } }
        }
        val tvs = async {
            tvChannels.channelsWithPlaylist( playlistPath ).map { tvChannelMapper { it.toEntity() } }
        }
        movies.await() + tvs.await()
    }

    /** Store a [IChannel] in the appropriate [ChannelsLocalSource] */
    private fun createChannel( channel: IChannel) {
        when( channel ) {
            is MovieChannel -> movieChannels.createChannel( movieChannelMapper { channel.toPojo() } )
            is TvChannel -> tvChannels.createChannel( tvChannelMapper { channel.toPojo() } )
            else -> throw ChannelNotImplementedException( this::class, this::createChannel, channel::class )
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

    /** Delete the store [IChannel] with the given [IChannel.id] */
    override fun deleteChannel( channelId: String ) {
        handle { tvChannels.delete( channelId ) }
        handle { movieChannels.delete( channelId ) }
    }

    /** Delete the stored [Playlist] with the given [Playlist.path] */
    override fun deletePlaylist( playlistPath: String ) {
        playlists.delete( playlistPath )
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
            else -> throw ChannelNotImplementedException( this::class, this::mergeChannel, newChannel::class )
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
        updateTvGuide(oldGuide + newGuide )
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
        updateGroup(oldGroup + newGroup )
        return Result.SUCCESS
    }

    /**
     * Merge the given [newPlaylist] with the already existing [Playlist] with the same [Playlist.path]
     * @return [Result.SUCCESS] if the operation is succeed, else [Result.FAILURE] if some exception occurs while
     * retrieving the old [ChannelGroup]
     */
    private fun mergePlaylist( newPlaylist: Playlist ): Result {
        val oldPlaylist = handle { playlistMapper { playlists.playlist( newPlaylist.path ).toEntity() } }
        oldPlaylist ?: return Result.FAILURE
        updatePlaylist(oldPlaylist + newPlaylist )
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
        val pojos = groupName?.let { movieChannels.channelsWithGroup( it ) } ?: movieChannels.all()
        return pojos.map { movieChannelMapper { it.toEntity() } }
    }

    /** @return the [ChannelGroup] with [ChannelGroup.Type.MOVIE] */
    override fun movieGroups(): List<ChannelGroup> =
        channelGroups.allMovie().map { channelGroupMapper { it.toEntity() } }

    /** @return all the stored [Playlist]s */
    override fun playlists(): List<Playlist> =
        playlists.all().map { playlistMapper { it.toEntity() } }

    /** Store the given [IChannel] in [ChannelsLocalSource]s */
    override fun storeChannel( channel: IChannel ) {
        if ( mergeChannel( channel ).isFailed ) createChannel( channel )
    }

    /** Store the given [ChannelGroup] in Local Source */
    override fun storeGroup( group: ChannelGroup ) {
        if ( mergeGroup( group ).isFailed ) createGroup( group )
    }

    /** Store the given [Playlist] in Local Source */
    override fun storePlaylist( playlist: Playlist ) {
        if ( mergePlaylist( playlist ).isFailed ) createPlaylist( playlist )
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
        val pojos = groupName?.let { tvChannels.channelsWithGroup( it ) } ?: tvChannels.all()
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
            else -> throw ChannelNotImplementedException( this::class, this::updateChannel, channel::class )
        }
    }

    /** Update a [ChannelGroup] in [ChannelGroupsLocalSource] */
    override fun updateGroup( group: ChannelGroup ) {
        channelGroups.updateGroup( channelGroupMapper { group.toPojo() } )
    }

    /** Update a [Playlist] in Local Source */
    override fun updatePlaylist( playlist: Playlist ) {
        playlists.updatePlaylist( playlistMapper { playlist.toPojo() } )
    }

    /** Update a [TvGuide] in [TvGuidesLocalSource] */
    override fun updateTvGuide( guide: TvGuide ) {
        tvGuides.updateGuide( tvGuideMapper { guide.toPojo() } )
    }
}