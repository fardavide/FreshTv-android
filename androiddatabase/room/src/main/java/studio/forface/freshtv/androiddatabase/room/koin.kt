package studio.forface.freshtv.androiddatabase.room

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.androiddatabase.localdata.AbsPagedLocalData
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.androiddatabase.room.localdata.RoomPagedLocalData
import studio.forface.freshtv.localdata.room.AppDatabase

/** A [Module] that handles dependencies of `androiddatabase` module with Room database */
val roomAndroidDatabaseModule = module {
    single( createdAtStart = true ) {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "Room-database"
        ).build()
    }

    factory<PagedLocalData> {
        RoomPagedLocalData(
            movieChannels = get(),
            tvChannels = get(),
            movieChannelMapper = get(),
            tvChannelMapper = get()
        )
    }
}