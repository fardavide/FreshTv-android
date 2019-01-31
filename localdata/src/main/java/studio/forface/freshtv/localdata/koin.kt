package studio.forface.freshtv.localdata

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.localdata.sources.*

/** A [Module] that handles dependencies for `localData` module */
val localData = module {

    /* Main */
    factory<LocalData> { LocalDataImpl(
            channelGroups = get(),
            movieChannels = get(),
            playlists = get(),
            tvChannels = get(),
            tvGuides = get()
    ) }

    /* Sources */
    factory { ChannelGroupsLocalSource(get()) }
    factory { MovieChannelsLocalSource(get()) }
    factory { PlaylistsLocalSource(get()) }
    factory { TvChannelsLocalSource(get()) }
    factory { TvGuidesLocalSource(get()) }

    /* Queries */
    val database: Database = get()
    factory { database.channelGroupQueries }
    factory { database.movieChannelQueries }
    factory { database.playlistQueries }
    factory { database.tvChannelQueries }
    factory { database.tvGuideQueries }

    /* Pojo adapters */
    factory { MovieChannelPojo.Adapter( get(), get() ) }
    factory { TvChannelPojo.Adapter( get(), get() ) }
    factory { TvGuidePojo.Adapter( get(), get() ) }

    /* Column adapters */
    factory { DateTimeColumnAdapter }
    factory { StringIntMapColumnAdapter }
    factory { StringListColumnAdapter }
}