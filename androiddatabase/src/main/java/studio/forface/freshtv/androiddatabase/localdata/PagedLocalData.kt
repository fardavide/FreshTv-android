package studio.forface.freshtv.androiddatabase.localdata

import androidx.paging.DataSource
import com.squareup.sqldelight.android.paging.QueryDataSourceFactory
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.localdata.MovieChannelQueries
import studio.forface.freshtv.localdata.TvChannelQueries
import studio.forface.freshtv.localdata.mappers.MovieChannelPojoMapper
import studio.forface.freshtv.localdata.mappers.TvChannelPojoMapper

/**
 * @author Davide Giuseppe Farella.
 * A repository for retrieve Paged [IChannel]s and EPG info locally.
 */
internal class PagedLocalData(
        private val movieChannels: MovieChannelQueries,
        private val tvChannels: TvChannelQueries,
        private val movieChannelMapper: MovieChannelPojoMapper,
        private val tvChannelMapper: TvChannelPojoMapper
) {

    /** @return a [DataSource.Factory] of all the [MovieChannel]s from Local Source */
    fun movieChannels(): DataSource.Factory<Int, MovieChannel> = QueryDataSourceFactory(
            queryProvider = movieChannels::selectPaged,
            countQuery = movieChannels.count()
    ).map { movieChannelMapper { it.toEntity() } }

    /**
     * @return a [DataSource.Factory] of all the [MovieChannel]s from Local Source with the given
     * [IChannel.groupName]
     * @param groupName a [String] filter for [IChannel.groupName]
     */
    fun movieChannels( groupName: String ): DataSource.Factory<Int, MovieChannel> = QueryDataSourceFactory(
            queryProvider = { l, o ->  movieChannels.selectPagedByGroup( groupName, l, o ) },
            countQuery = movieChannels.countByGroup( groupName )
    ).map { movieChannelMapper { it.toEntity() } }

    /** @return a [DataSource.Factory] of all the [TvChannel]s from Local Source */
    fun tvChannels(): DataSource.Factory<Int, TvChannel> = QueryDataSourceFactory(
            queryProvider = tvChannels::selectPaged,
            countQuery = tvChannels.count()
    ).map { tvChannelMapper { it.toEntity() } }

    /**
     * @return a [DataSource.Factory] of all the [TvChannel]s from Local Source with the given
     * [IChannel.groupName]
     * @param groupName a [String] filter for [IChannel.groupName]
     */
    fun tvChannels( groupName: String ): DataSource.Factory<Int, TvChannel> = QueryDataSourceFactory(
            queryProvider = { l, o ->  tvChannels.selectPagedByGroup( groupName, l, o ) },
            countQuery = tvChannels.countByGroup( groupName )
    ).map { tvChannelMapper { it.toEntity() } }
}