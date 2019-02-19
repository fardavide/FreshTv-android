package studio.forface.freshtv.localdata.room

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.localdata.room.sources.*
import studio.forface.freshtv.localdata.sources.*

/** A [Module] that handles dependencies for `localData` module with Room database */
val roomLocalDataModule = module {

    /* Main */
    factory<LocalData> {
        RoomLocalData(
            channelGroups = get(),
            movieChannels = get(),
            sourceFiles = get(),
            tvChannels = get(),
            tvGuides = get()
        )
    }

    /* Sources */
    factory<ChannelGroupsLocalSource<*>> { RoomChannelGroupsLocalSource( get() ) }
    factory<MovieChannelsLocalSource<*>> { RoomMovieChannelsLocalSource( get() ) }
    factory<SourceFilesLocalSource<*>> { RoomSourceFilesLocalSource( get() ) }
    factory<TvChannelsLocalSource<*>> { RoomTvChannelsLocalSource( get() ) }
    factory<TvGuidesLocalSource<*>> { RoomTvGuidesLocalSource( get() ) }

    /* Dao's */
    factory { get<AppDatabase>().channelGroupDao }
    factory { get<AppDatabase>().movieChannelDao }
    factory { get<AppDatabase>().sourceFileDao }
    factory { get<AppDatabase>().tvChannelDao }
    factory { get<AppDatabase>().tvGuideDao }
}