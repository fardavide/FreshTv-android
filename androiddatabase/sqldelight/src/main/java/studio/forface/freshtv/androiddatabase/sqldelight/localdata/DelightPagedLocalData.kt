package studio.forface.freshtv.androiddatabase.sqldelight.localdata

import studio.forface.freshtv.androiddatabase.localdata.AbsPagedLocalData
import studio.forface.freshtv.androiddatabase.sqldelight.sources.DelightPagedMovieChannelsLocalSource
import studio.forface.freshtv.androiddatabase.sqldelight.sources.DelightPagedSourceFilesLocalSource
import studio.forface.freshtv.androiddatabase.sqldelight.sources.DelightPagedTvChannelsLocalSource
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.sqldelight.MovieChannelPojo
import studio.forface.freshtv.localdata.sqldelight.SourceFilePojo
import studio.forface.freshtv.localdata.sqldelight.TvChannelPojo
import studio.forface.freshtv.localdata.sqldelight.mappers.DelightMovieChannelPojoMapper
import studio.forface.freshtv.localdata.sqldelight.mappers.DelightSourceFilePojoMapper
import studio.forface.freshtv.localdata.sqldelight.mappers.DelightTvChannelPojoMapper
import studio.forface.freshtv.localdata.sqldelight.mappers.DelightTvChannelWithGuidePojoMapper
import studio.forface.freshtv.localdata.sqldelight.utils.TvChannelWithGuidePojo

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve Paged [IChannel]s and EPG info locally.
 */
internal class DelightPagedLocalData(
    movieChannels: DelightPagedMovieChannelsLocalSource,
    sourceFiles: DelightPagedSourceFilesLocalSource,
    tvChannels: DelightPagedTvChannelsLocalSource,
    movieChannelMapper: DelightMovieChannelPojoMapper,
    sourceFilesMapper: DelightSourceFilePojoMapper,
    tvChannelMapper: DelightTvChannelPojoMapper,
    tvChannelWithGuideMapper: DelightTvChannelWithGuidePojoMapper
) : AbsPagedLocalData<MovieChannelPojo, SourceFilePojo, TvChannelPojo, TvChannelWithGuidePojo>(
    movieChannels,
    sourceFiles,
    tvChannels,
    movieChannelMapper,
    sourceFilesMapper,
    tvChannelMapper,
    tvChannelWithGuideMapper
)