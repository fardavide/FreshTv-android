package studio.forface.freshtv.localdata.room

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.AbsLocalData
import studio.forface.freshtv.localdata.room.entities.ChannelGroupPojo
import studio.forface.freshtv.localdata.room.entities.MovieChannelPojo
import studio.forface.freshtv.localdata.room.entities.SourceFilePojo
import studio.forface.freshtv.localdata.room.mappers.RoomChannelGroupPojoMapper
import studio.forface.freshtv.localdata.room.mappers.RoomMovieChannelPojoMapper
import studio.forface.freshtv.localdata.room.mappers.RoomSourceFilePojoMapper
import studio.forface.freshtv.localdata.room.sources.RoomChannelGroupsLocalSource
import studio.forface.freshtv.localdata.room.sources.RoomMovieChannelsLocalSource
import studio.forface.freshtv.localdata.room.sources.RoomSourceFilesLocalSource

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve and store [IChannel]s and EPG info locally.
 */
internal class RoomLocalData(
    channelGroups: RoomChannelGroupsLocalSource,
    movieChannels: RoomMovieChannelsLocalSource,
    sourceFiles: RoomSourceFilesLocalSource,
    tvChannels: RoomTvChannelsLocalSource,
    tvGuides: RoomTvGuidesLocalSource,
    channelGroupMapper: RoomChannelGroupPojoMapper = RoomChannelGroupPojoMapper(),
    movieChannelMapper: RoomMovieChannelPojoMapper = RoomMovieChannelPojoMapper(),
    sourceFileMapper: RoomSourceFilePojoMapper = RoomSourceFilePojoMapper(),
    tvChannelMapper: RoomTvChannelPojoMapper = RoomTvChannelPojoMapper(),
    tvGuideMapper: RoomTvGuidePojoMapper = RoomTvGuidePojoMapper()
) : AbsLocalData<
        ChannelGroupPojo,
        MovieChannelPojo,
        SourceFilePojo,
        TvChannelPojo,
        TvGuidePojo
>(
    channelGroups =         channelGroups,
    movieChannels =         movieChannels,
    sourceFiles =           sourceFiles,
    tvChannels =            tvChannels,
    tvGuides =              tvGuides,
    channelGroupMapper =    channelGroupMapper,
    movieChannelMapper =    movieChannelMapper,
    sourceFileMapper =      sourceFileMapper,
    tvChannelMapper =       tvChannelMapper,
    tvGuideMapper =         tvGuideMapper
)