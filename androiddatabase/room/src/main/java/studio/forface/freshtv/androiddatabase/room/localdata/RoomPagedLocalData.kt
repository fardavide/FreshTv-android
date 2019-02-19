package studio.forface.freshtv.androiddatabase.room.localdata

import studio.forface.freshtv.androiddatabase.localdata.AbsPagedLocalData
import studio.forface.freshtv.androiddatabase.room.sources.RoomPagedMovieChannelsLocalSource
import studio.forface.freshtv.androiddatabase.room.sources.RoomPagedTvChannelsLocalSource
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.localdata.room.entities.MovieChannelPojo
import studio.forface.freshtv.localdata.room.entities.TvChannelPojo
import studio.forface.freshtv.localdata.room.mappers.RoomMovieChannelPojoMapper
import studio.forface.freshtv.localdata.room.mappers.RoomTvChannelPojoMapper

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve Paged [IChannel]s and EPG info locally.
 */
internal class RoomPagedLocalData(
    movieChannels: RoomPagedMovieChannelsLocalSource,
    tvChannels: RoomPagedTvChannelsLocalSource,
    movieChannelMapper: RoomMovieChannelPojoMapper,
    tvChannelMapper: RoomTvChannelPojoMapper
) : AbsPagedLocalData<MovieChannelPojo, TvChannelPojo>(
    movieChannels, tvChannels, movieChannelMapper, tvChannelMapper
)