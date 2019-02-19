package studio.forface.freshtv.androiddatabase.sqldelight.localdata

import studio.forface.freshtv.androiddatabase.localdata.AbsPagedLocalData
import studio.forface.freshtv.androiddatabase.sqldelight.sources.DelightPagedMovieChannelsLocalSource
import studio.forface.freshtv.androiddatabase.sqldelight.sources.DelightPagedTvChannelsLocalSource
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.sqldelight.MovieChannelPojo
import studio.forface.freshtv.localdata.sqldelight.TvChannelPojo
import studio.forface.freshtv.localdata.sqldelight.mappers.DelightMovieChannelPojoMapper
import studio.forface.freshtv.localdata.sqldelight.mappers.DelightTvChannelPojoMapper

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve Paged [IChannel]s and EPG info locally.
 */
internal class DelightPagedLocalData(
    movieChannels: DelightPagedMovieChannelsLocalSource,
    tvChannels: DelightPagedTvChannelsLocalSource,
    movieChannelMapper: DelightMovieChannelPojoMapper,
    tvChannelMapper: DelightTvChannelPojoMapper
) : AbsPagedLocalData<MovieChannelPojo, TvChannelPojo>(
    movieChannels, tvChannels, movieChannelMapper, tvChannelMapper
)