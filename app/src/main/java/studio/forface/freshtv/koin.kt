package studio.forface.freshtv

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.presenters.ChannelsAvailabilityPresenter
import studio.forface.freshtv.presenters.PlaylistPresenter
import studio.forface.freshtv.viewmodels.ChannelsAvailabilityViewModel
import studio.forface.freshtv.viewmodels.EditPlaylistViewModel

/** A [Module] that handles dependencies for use cases of `app` module */
val appModule = module {

    /* Interactors */
    factory { EditPlaylistInteractor( addPlaylist = get(), updatePlaylist = get() ) }

    /* Mappers */
    factory { SourceFileUiModelMapper() }

    /* Presenters */
    factory { ChannelsAvailabilityPresenter( hasMovieChannels = get(), hasTvChannels = get() ) }
    factory { PlaylistPresenter( getPlaylist = get(), mapper = get() ) }

    /* View Models */
    viewModel { ChannelsAvailabilityViewModel( presenter = get() ) }
    viewModel { (playlistPath: String?) -> EditPlaylistViewModel(
            interactor = get(),
            presenter = get(),
            playlistPath = playlistPath
    ) }
}