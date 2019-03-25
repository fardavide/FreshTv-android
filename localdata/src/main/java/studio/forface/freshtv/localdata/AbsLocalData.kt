@file:Suppress("FoldInitializerAndIfToElvis")

package studio.forface.freshtv.localdata

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.*
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
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
abstract class AbsLocalData<
        ChannelGroupPojo,
        MovieChannelPojo,
        SourceFilePojo,
        TvChannelPojo,
        TvGuidePojo
>(
    private val channelGroups: ChannelGroupsLocalSource<ChannelGroupPojo>,
    private val movieChannels: MovieChannelsLocalSource<MovieChannelPojo>,
    private val sourceFiles: SourceFilesLocalSource<SourceFilePojo>,
    private val tvChannels: TvChannelsLocalSource<TvChannelPojo>,
    private val tvGuides: TvGuidesLocalSource<TvGuidePojo>,
    private val channelGroupMapper: ChannelGroupPojoMapper<ChannelGroupPojo>,
    private val movieChannelMapper: MovieChannelPojoMapper<MovieChannelPojo>,
    private val sourceFileMapper: SourceFilePojoMapper<SourceFilePojo>,
    private val tvChannelMapper: TvChannelPojoMapper<TvChannelPojo>,
    private val tvGuideMapper: TvGuidePojoMapper<TvGuidePojo>
) : LocalData {

    /** @return [ReceiveChannel] of the [IChannel] with the given [channelId] */
    override suspend fun observeChannel( channelId: String ): ReceiveChannel<IChannel> {
        val channel = channel( channelId )
        return when ( channel ) {
            is MovieChannel -> movieChannels.obServeChannel( channelId ).map( movieChannelMapper ) { it.toEntity() }
            is TvChannel -> tvChannels.obServeChannel( channelId ).map( tvChannelMapper ) { it.toEntity() }
            else -> throw NotImplementedError()
        }
    }

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

    /** @return the [Int] count of the stored [MovieChannel]s */
    override fun countMovieChannels(): Int = movieChannels.count()

    /** @return [ReceiveChannel] of the [Int] count of the stored [MovieChannel]s */
    override suspend fun observeCountMovieChannels() = movieChannels.observeCount()

    /** @return the [Int] count of the stored [TvChannel]s */
    override fun countTvChannels(): Int = tvChannels.count()

    /** @return [ReceiveChannel] of the [Int] count of the stored [TvChannel]s */
    override suspend fun observeCountTvChannels() = tvChannels.observeCount()

    /** Store a [IChannel] in the appropriate [ChannelsLocalSource] */
    private fun createChannel( channel: IChannel) {
        when( channel ) {
            is MovieChannel -> movieChannels.createChannel( movieChannelMapper { channel.toPojo() } )
            is TvChannel -> tvChannels.createChannel( tvChannelMapper { channel.toPojo() } )
            else -> throw ChannelNotImplementedException( this::class, this::createChannel, channel::class )
        }
    }

    /** Store a [ChannelGroup] in [SourceFilesLocalSource] */
    private fun createEpg( epg: Epg) {
        sourceFiles.create( sourceFileMapper { epg.toPojo() } )
    }

    /** Store a [ChannelGroup] in [ChannelGroupsLocalSource] */
    private fun createGroup( group: ChannelGroup) {
        channelGroups.createChannelGroup( channelGroupMapper { group.toPojo() } )
    }

    /** Store a [Playlist] in [SourceFilesLocalSource] */
    private fun createPlaylist( playlist: Playlist) {
        sourceFiles.create( sourceFileMapper { playlist.toPojo() } )
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

    /** Delete all the stored [ChannelGroup]s */
    override fun deleteAllGroups() {
        channelGroups.deleteAll()
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

    /** Delete the stored [Epg] with the given [Epg.path] */
    override fun deleteEpg( epgPath: String ) {
        sourceFiles.delete( epgPath )
    }

    /** Delete the stored [ChannelGroup] with the given [ChannelGroup.id] */
    override fun deleteGroup( groupId: String ) {
        channelGroups.delete( groupId )
    }

    /** Delete the stored [Playlist] with the given [Playlist.path] */
    override fun deletePlaylist( playlistPath: String ) {
        sourceFiles.delete( playlistPath )
    }

    /** Delete all the [TvGuide]s from Local Source with [TvGuide.endTime] less that the given [dateTime] */
    override fun deleteTvGuidesBefore( dateTime: LocalDateTime ) = tvGuides.deleteGuidesBefore( dateTime )

    /** @return the stored [Epg] with the given [epgPath] */
    override fun epg( epgPath: String ): Epg =
        sourceFileMapper { sourceFiles.epg( epgPath ).toEntity() as Epg }

    /** @return all the stored [Epg]s */
    override fun epgs(): List<Epg> =
        sourceFiles.allEpgs().map { sourceFileMapper { it.toEntity() } as Epg }

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

        // return FAILURE if oldChannel is not found
        if ( oldChannel == null ) return Result.FAILURE
        // return SUCCESS if oldChannel is same as newChannel
        if ( oldChannel == newChannel ) return Result.SUCCESS

        updateChannel(oldChannel + newChannel )
        return Result.SUCCESS
    }

    /**
     * Merge the given [newEpg] with the already existing [Epg] with the same [Epg.path]
     * @return [Result.SUCCESS] if the operation is succeed, else [Result.FAILURE] if some exception occurs while
     * retrieving the old [Epg]
     */
    private fun mergeEpg( newEpg: Epg ): Result {
        val oldEpg = handle { sourceFileMapper { sourceFiles.epg( newEpg.path ).toEntity() as Epg } }

        // return FAILURE if oldEpg is not found
        if ( oldEpg == null ) return Result.FAILURE
        // return SUCCESS if oldEpg is same as newEpg
        if ( oldEpg == newEpg ) return Result.SUCCESS

        updateEpg(oldEpg + newEpg )
        return Result.SUCCESS
    }

    /**
     * Merge the given [newGuide] with the already existing [TvGuide] with the same [TvGuide.id]
     * @return [Result.SUCCESS] if the operation is succeed, else [Result.FAILURE] if some exception occurs while
     * retrieving the old [TvGuide]
     */
    private fun mergeGuide( newGuide: TvGuide ): Result {
        val oldGuide = handle { tvGuideMapper { tvGuides.guide( newGuide.id ).toEntity() } }

        // return FAILURE if oldGuide is not found
        if ( oldGuide == null ) return Result.FAILURE
        // return SUCCESS if oldGuide is same as newGuide
        if ( oldGuide == newGuide ) return Result.SUCCESS

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

        // return FAILURE if oldGroup is not found
        if ( oldGroup == null ) return Result.FAILURE
        // return SUCCESS if oldGroup is same as newGroup
        if ( oldGroup == newGroup ) return Result.SUCCESS

        updateGroup(oldGroup + newGroup )
        return Result.SUCCESS
    }

    /**
     * Merge the given [newPlaylist] with the already existing [Playlist] with the same [Playlist.path]
     * @return [Result.SUCCESS] if the operation is succeed, else [Result.FAILURE] if some exception occurs while
     * retrieving the old [ChannelGroup]
     */
    private fun mergePlaylist( newPlaylist: Playlist): Result {
        val oldPlaylist = handle { sourceFileMapper {
            sourceFiles.playlist( newPlaylist.path ).toEntity() as Playlist
        } }

        // return FAILURE if oldPlaylist is not found
        if ( oldPlaylist == null ) return Result.FAILURE
        // return SUCCESS if oldPlaylist is same as newPlaylist
        if ( oldPlaylist == newPlaylist ) return Result.SUCCESS

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

    /** @return [Playlist] with the given [playlistPath] */
    override fun playlist( playlistPath: String ) : Playlist =
        sourceFileMapper { sourceFiles.playlist( playlistPath ).toEntity() as Playlist }

    /** @return all the stored [Playlist]s */
    override fun playlists(): List<Playlist> =
        sourceFiles.allPlaylists().map { sourceFileMapper { it.toEntity() } as Playlist }

    /** Store the given [IChannel] in [ChannelsLocalSource]s */
    override fun storeChannel( channel: IChannel ) {
        if ( mergeChannel( channel ).isFailed ) createChannel( channel )
    }

    /** Store the given [Epg] in Local Source */
    override fun storeEpg( epg: Epg ) {
        if ( mergeEpg( epg ).isFailed ) createEpg( epg )
    }

    /** Store the given [ChannelGroup] in Local Source */
    override fun storeGroup( group: ChannelGroup ) {
        if ( mergeGroup( group ).isFailed ) createGroup( group )
    }

    /** Store the given [Playlist] in Local Source */
    override fun storePlaylist( playlist: Playlist ) {
        if ( mergePlaylist( playlist ).isFailed ) createPlaylist( playlist )
    }

    /** Store the given [TvGuide] in [TvGuidesLocalSource]s */
    override fun storeTvGuide( guide: TvGuide ) {
        if ( mergeGuide( guide ).isFailed ) createTvGuide( guide )
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
     * @return the [TvGuide] from [TvGuidesLocalSource] matching the given [channelId] and [time]
     * @see TvGuidesLocalSource.guideForChannel for parameters details.
     */
    override fun tvGuides( channelId: String, time: LocalDateTime? ): TvGuide =
            tvGuideMapper { tvGuides.guideForChannel( channelId, time ).toEntity() }

    /**
     * @return all the [TvGuide]s from [TvGuidesLocalSource] matching the given [channelId], [from] and [to]
     * @see TvGuidesLocalSource.guidesForChannelRanged for parameters details.
     */
    override fun tvGuidesRanged(channelId: String, from: LocalDateTime?, to: LocalDateTime? ): List<TvGuide> =
            tvGuides.guidesForChannelRanged( channelId, from, to ).map { tvGuideMapper { it.toEntity() } }

    /** Update a [IChannel] in the appropriate [ChannelsLocalSource] */
    override fun updateChannel( channel: IChannel ) {
        when ( channel ) {
            is MovieChannel -> movieChannels.updateChannel( movieChannelMapper { channel.toPojo() } )
            is TvChannel -> tvChannels.updateChannel( tvChannelMapper { channel.toPojo() } )
            else -> throw ChannelNotImplementedException( this::class, this::updateChannel, channel::class )
        }
    }

    /** Update an [Epg] in [SourceFilesLocalSource] */
    override fun updateEpg( epg: Epg ) {
        sourceFiles.update( sourceFileMapper { epg.toPojo() } )
    }

    /** Update a [ChannelGroup] in [ChannelGroupsLocalSource] */
    override fun updateGroup( group: ChannelGroup ) {
        channelGroups.updateGroup( channelGroupMapper { group.toPojo() } )
    }

    /** Update a [Playlist] in [SourceFilesLocalSource] */
    override fun updatePlaylist( playlist: Playlist ) {
        sourceFiles.update( sourceFileMapper { playlist.toPojo() } )
    }

    /** Update a [TvGuide] in [TvGuidesLocalSource] */
    override fun updateTvGuide( guide: TvGuide ) {
        tvGuides.updateGuide( tvGuideMapper { guide.toPojo() } )
    }
}