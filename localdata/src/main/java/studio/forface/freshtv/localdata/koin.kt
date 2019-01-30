package studio.forface.freshtv.localdata

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import studio.forface.freshtv.domain.gateways.LocalData

/** A [Module] that handles dependencies for `localData` module */
val localData = module {
    factory<LocalData> { LocalDataImpl(
            channelGroups = get(),
            movieChannels = get(),
            playlists = get(),
            tvChannels = get(),
            tvGuides = get()
    ) }

    factory { ChannelGroupsLocalSource( get() ) } // TODO: ChannelGroupQueries
    factory { MovieChannelsLocalSource( get() ) } // TODO: MovieChannelQueries
    factory { PlaylistsLocalSource( get() ) } // TODO: PlaylistQueries
    factory { TvChannelsLocalSource( get() ) } // TODO: TvChannelQueries
    factory { TvGuidesLocalSource( get() ) } // TODO: TvGuideQueries
}