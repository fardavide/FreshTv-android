package studio.forface.freshtv.androiddatabase

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.androiddatabase.usecases.GetPagedMovieChannels
import studio.forface.freshtv.androiddatabase.usecases.GetPagedTvChannels

/** A [Module] that handles dependencies of `androiddatabase` module */
val androidDatabaseModule = module {
    /* USE CASES */
    factory { GetPagedMovieChannels( localData = get() ) }
    factory { GetPagedTvChannels( localData = get() ) }
}