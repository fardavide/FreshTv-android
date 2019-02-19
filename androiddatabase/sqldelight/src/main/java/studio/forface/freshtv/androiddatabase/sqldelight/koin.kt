package studio.forface.freshtv.androiddatabase.sqldelight

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.androiddatabase.sqldelight.localdata.DelightPagedLocalData
import studio.forface.freshtv.androiddatabase.sqldelight.sources.DelightPagedMovieChannelsLocalSource
import studio.forface.freshtv.androiddatabase.sqldelight.sources.DelightPagedTvChannelsLocalSource
import studio.forface.freshtv.localdata.sqldelight.Database

/** A [Module] that handles dependencies of `androiddatabase` module with SqlDelight database */
val sqlDelightAndroidDatabaseModule = module {
    single<SqlDriver>( createdAtStart = true ) {
        AndroidSqliteDriver(
            Database.Schema,
            androidApplication(),
            "SqlDelight-database"
        )
    }
    single( createdAtStart = true ) {
        Database(
            driver = get(),
            movieChannelPojoAdapter = get(),
            tvChannelPojoAdapter = get(),
            tvGuidePojoAdapter = get()
        )
    }

    /* Source */
    factory { DelightPagedMovieChannelsLocalSource( queries = get() ) }
    factory { DelightPagedTvChannelsLocalSource( queries = get() ) }

    /* Use cases */
    factory<PagedLocalData> {
        DelightPagedLocalData(
            movieChannels = get(),
            tvChannels = get(),
            movieChannelMapper = get(),
            tvChannelMapper = get()
        )
    }
}