package studio.forface.freshtv.preferences

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.preferences.mappers.ChannelsDatabaseStateUiModelMapper
import studio.forface.freshtv.preferences.presenters.CleanChannelsPresenter
import studio.forface.freshtv.preferences.presenters.CleanChannelsPresenterImpl
import studio.forface.freshtv.preferences.presenters.PreferencesPresenter

/** A [Module] that handles dependencies of `preferences` module */
val preferencesModule = module {

    /* Interactors */

    /* Mappers */
    factory { ChannelsDatabaseStateUiModelMapper() }

    /* Presenters */
    factory { PreferencesPresenter() }
    factory<CleanChannelsPresenter> { CleanChannelsPresenterImpl(
        hasMovieChannels = get(),
        hasTvChannels = get(),
        mapper = get()
    ) }

    /* View Models */

}