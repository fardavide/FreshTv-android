package studio.forface.freshtv.androiddatabase.localdata

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.sources.PagedMovieChannelsLocalSource
import studio.forface.freshtv.androiddatabase.sources.PagedTvChannelsLocalSource
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.localdata.mappers.MovieChannelPojoMapper
import studio.forface.freshtv.localdata.mappers.TvChannelPojoMapper
import studio.forface.freshtv.localdata.mappers.invoke

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve Paged [IChannel]s and EPG info locally.
 */
abstract class AbsPagedLocalData<MovieChannelPojo, TvChannelPojo>(
        private val movieChannels: PagedMovieChannelsLocalSource<MovieChannelPojo>,
        private val tvChannels: PagedTvChannelsLocalSource<TvChannelPojo>,
        private val movieChannelMapper: MovieChannelPojoMapper<MovieChannelPojo>,
        private val tvChannelMapper: TvChannelPojoMapper<TvChannelPojo>
) {

    /** @return a [DataSource.Factory] of all the [MovieChannel]s from Local Source */
    fun movieChannels(): DataSource.Factory<Int, MovieChannel> = movieChannels.all()
        .map { movieChannelMapper { it.toEntity() } }

    /**
     * @return a [DataSource.Factory] of all the [MovieChannel]s from Local Source with the given
     * [IChannel.groupName]
     * @param groupName a [String] filter for [IChannel.groupName]
     */
    fun movieChannels( groupName: String ) = movieChannels.channelsWithGroup( groupName )
        .map { movieChannelMapper { it.toEntity() } }

    /** @return a [DataSource.Factory] of all the [TvChannel]s from Local Source */
    fun tvChannels(): DataSource.Factory<Int, TvChannel> = tvChannels.all()
        .map { tvChannelMapper { it.toEntity() } }

    /**
     * @return a [DataSource.Factory] of all the [TvChannel]s from Local Source with the given
     * [IChannel.groupName]
     * @param groupName a [String] filter for [IChannel.groupName]
     */
    fun tvChannels( groupName: String ) = tvChannels.channelsWithGroup( groupName )
        .map { tvChannelMapper { it.toEntity() } }
}

/** A typealias of [AbsPagedLocalData] with star-projected generics */
typealias PagedLocalData = AbsPagedLocalData<*,*>