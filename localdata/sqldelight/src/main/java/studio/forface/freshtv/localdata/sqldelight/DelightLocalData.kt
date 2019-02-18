package studio.forface.freshtv.localdata.sqldelight

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.AbsLocalData
import studio.forface.freshtv.localdata.sqldelight.mappers.*
import studio.forface.freshtv.localdata.sqldelight.sources.*

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve and store [IChannel]s and EPG info locally.
 */
internal class DelightLocalData(
    channelGroups: DelightChannelGroupsLocalSource,
    movieChannels: DelightMovieChannelsLocalSource,
    sourceFiles: DelightSourceFilesLocalSource,
    tvChannels: DelightTvChannelsLocalSource,
    tvGuides: DelightTvGuidesLocalSource,
    channelGroupMapper: DelightChannelGroupPojoMapper = DelightChannelGroupPojoMapper(),
    movieChannelMapper: DelightMovieChannelPojoMapper = DelightMovieChannelPojoMapper(),
    sourceFileMapper: DelightSourceFilePojoMapper = DelightSourceFilePojoMapper(),
    tvChannelMapper: DelightTvChannelPojoMapper = DelightTvChannelPojoMapper(),
    tvGuideMapper: DelightTvGuidePojoMapper = DelightTvGuidePojoMapper()
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