package studio.forface.freshtv.localdata.room

import com.squareup.sqldelight.ColumnAdapter
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.localdata.sources.*

/** A [Module] that handles dependencies for `localData` module */
val roomLocalDataModule = module {

    /* Main */
    factory<LocalData> { RoomLocalData(
            channelGroups = get(),
            movieChannels = get(),
            sourceFiles = get(),
            tvChannels = get(),
            tvGuides = get()
    ) }

    /* Sources */
    factory { ChannelGroupsLocalSource( get() ) }
    factory { MovieChannelsLocalSource( get() ) }
    factory { SourceFilesLocalSource( get() ) }
    factory { TvChannelsLocalSource( get() ) }
    factory { TvGuidesLocalSource( get() ) }

    /* Queries */
    factory { get<Database>().channelGroupQueries }
    factory { get<Database>().movieChannelQueries }
    factory { get<Database>().sourceFileQueries }
    factory { get<Database>().tvChannelQueries }
    factory { get<Database>().tvGuideQueries }

    /* Pojo adapters */
    factory { MovieChannelPojo.Adapter( get( CA_MAP_STRING_INT ), get( CA_LIST_STRING ) ) }
    factory { TvChannelPojo.Adapter( get( CA_MAP_STRING_INT ), get( CA_LIST_STRING ) ) }
    factory { TvGuidePojo.Adapter( get( CA_LIST_STRING ), get( CA_LOCAL_DATE_TIME ), get( CA_LOCAL_DATE_TIME ) ) }

    /* Column adapters */
    factory( CA_LOCAL_DATE_TIME ) { DateTimeColumnAdapter }
    factory( CA_MAP_STRING_INT ) { StringIntMapColumnAdapter }
    factory( CA_LIST_STRING ) { StringListColumnAdapter }
}

private const val CA_LIST_STRING = "column_adapter-list_string"
private const val CA_LOCAL_DATE_TIME = "column_adapter-local_date_time"
private const val CA_MAP_STRING_INT = "column_adapter-map_string_ing"