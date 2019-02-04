package studio.forface.freshtv.localdata

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.localdata.sources.*

/** A [Module] that handles dependencies for `localData` module */
val localDataModule = module {

    /* Main */
    factory<LocalData> { LocalDataImpl(
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
    factory { MovieChannelPojo.Adapter( get(), get() ) }
    factory { TvChannelPojo.Adapter( get(), get() ) }
    factory { TvGuidePojo.Adapter( get(), get(), get() ) }

    /* Column adapters */
    factory { DateTimeColumnAdapter }
    factory { StringIntMapColumnAdapter }
    factory { StringListColumnAdapter }
}