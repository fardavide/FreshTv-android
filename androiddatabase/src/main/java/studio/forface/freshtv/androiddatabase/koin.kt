package studio.forface.freshtv.androiddatabase

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.androiddatabase.usecases.GetPagedMovieChannels
import studio.forface.freshtv.androiddatabase.usecases.GetPagedTvChannels
import studio.forface.freshtv.localdata.Database

/** A [Module] that handles dependencies of `androiddatabase` module */
val androidDatabaseModule = module {
    single<SqlDriver>( createdAtStart = true ) { AndroidSqliteDriver(
            Database.Schema,
            androidApplication(),
            "FreshTv"
    ) }
    single( createdAtStart = true ) { Database(
            driver = get(),
            movieChannelPojoAdapter = get(),
            tvChannelPojoAdapter = get(),
            tvGuidePojoAdapter = get()
    ) }

    factory { PagedLocalData(
            movieChannels = get(),
            tvChannels = get(),
            movieChannelMapper = get(),
            tvChannelMapper = get()
    ) }

    /* USE CASES */
    factory { GetPagedMovieChannels( localData = get() ) }
    factory { GetPagedTvChannels( localData = get() ) }
}