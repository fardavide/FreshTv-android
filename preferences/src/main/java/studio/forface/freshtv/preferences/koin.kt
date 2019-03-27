package studio.forface.freshtv.preferences

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.preferences.interactors.*
import studio.forface.freshtv.preferences.mappers.ChannelsDatabaseStateUiModelMapper
import studio.forface.freshtv.preferences.mappers.GuidesDatabaseStateUiModelMapper
import studio.forface.freshtv.preferences.presenters.*
import studio.forface.freshtv.preferences.viewmodels.PreferencesViewModel

/** A [Module] that handles dependencies of `preferences` module */
val preferencesModule = module {

    /* Interactors */
    factory { EditPreferencesInteractor() }
    factory<CleanChannelsInteractor> {
        CleanChannelsInteractorImpl(
            removeAllChannels = get(), removeEmptyGroups = get()
        )
    }
    factory<CleanGuidesInteractor> { CleanGuidesInteractorImpl( removeAllGuides = get() ) }

    /* Mappers */
    factory { ChannelsDatabaseStateUiModelMapper() }
    factory { GuidesDatabaseStateUiModelMapper() }

    /* Presenters */
    factory { PreferencesPresenter() }
    factory<CleanChannelsPresenter> {
        CleanChannelsPresenterImpl(
            hasMovieChannels = get(), hasTvChannels = get(), mapper = get()
        )
    }
    factory<CleanGuidesPresenter> { CleanGuidesPresenterImpl( hasTvGuides = get(), mapper = get() ) }

    /* View Models */
    viewModel { PreferencesViewModel( presenter = get(), interactor = get() ) }
}