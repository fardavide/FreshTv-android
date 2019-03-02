package studio.forface.freshtv.localdata.sqldelight

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.localdata.sqldelight.mappers.*
import studio.forface.freshtv.localdata.sqldelight.sources.*

/** A [Module] that handles dependencies for `localData` module with SqlDelight database */
val sqlDelightLocalDataModule = module {

    /* Main */
    factory<LocalData> {
        DelightLocalData(
            channelGroups = get(),
            movieChannels = get(),
            sourceFiles = get(),
            tvChannels = get(),
            tvGuides = get()
        )
    }

    /* Sources */
    factory { DelightChannelGroupsLocalSource( get() ) }
    factory { DelightMovieChannelsLocalSource( get() ) }
    factory { DelightSourceFilesLocalSource( get() ) }
    factory { DelightTvChannelsLocalSource( get() ) }
    factory { DelightTvGuidesLocalSource( get() ) }

    /* Queries */
    factory { get<Database>().channelGroupQueries }
    factory { get<Database>().movieChannelQueries }
    factory { get<Database>().sourceFileQueries }
    factory { get<Database>().tvChannelQueries }
    factory { get<Database>().tvGuideQueries }

    /* Mappers */
    factory { DelightChannelGroupPojoMapper() }
    factory { DelightMovieChannelPojoMapper() }
    factory { DelightSourceFilePojoMapper() }
    factory { DelightTvChannelPojoMapper() }
    factory { DelightTvGuidePojoMapper() }

    /* Pojo adapters */
    factory { MovieChannelPojo.Adapter( get( CA_MAP_STRING_INT ), get( CA_SET_STRING ) ) }
    factory { TvChannelPojo.Adapter( get( CA_MAP_STRING_INT ), get( CA_SET_STRING ) ) }
    factory { TvGuidePojo.Adapter( get( CA_LIST_STRING ), get( CA_LOCAL_DATE_TIME ), get( CA_LOCAL_DATE_TIME ) ) }

    /* Column adapters */
    factory( CA_LIST_STRING ) { StringListColumnAdapter }
    factory( CA_LOCAL_DATE_TIME ) { DateTimeColumnAdapter }
    factory( CA_MAP_STRING_INT ) { StringIntMapColumnAdapter }
    factory( CA_SET_STRING ) { StringSetColumnAdapter }
}

private const val CA_LIST_STRING = "column_adapter-list_string"
private const val CA_LOCAL_DATE_TIME = "column_adapter-local_date_time"
private const val CA_MAP_STRING_INT = "column_adapter-map_string_ing"
private const val CA_SET_STRING = "column_adapter-set_string"