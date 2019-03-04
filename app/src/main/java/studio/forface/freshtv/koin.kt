package studio.forface.freshtv

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.mappers.TvChannelUiModelMapper
import studio.forface.freshtv.presenters.ChannelsAvailabilityPresenter
import studio.forface.freshtv.presenters.PlaylistPresenter
import studio.forface.freshtv.presenters.PlaylistsPresenter
import studio.forface.freshtv.presenters.TvChannelsPresenter
import studio.forface.freshtv.viewmodels.ChannelsAvailabilityViewModel
import studio.forface.freshtv.viewmodels.EditPlaylistViewModel
import studio.forface.freshtv.viewmodels.PlaylistsViewModel
import studio.forface.freshtv.viewmodels.TvChannelsViewModel

/** A [Module] that handles dependencies for use cases of `app` module */
val appModule = module {

    /* Interactors */
    factory { ChannelChangeFavoriteInteractor( updateChannelFavoriteState = get() ) }
    factory { EditPlaylistInteractor( addPlaylist = get(), updatePlaylist = get() ) }

    /* Mappers */
    factory { SourceFileUiModelMapper() }
    factory { TvChannelUiModelMapper() }

    /* Presenters */
    factory { ChannelsAvailabilityPresenter( hasMovieChannels = get(), hasTvChannels = get() ) }
    factory { PlaylistPresenter( getPlaylist = get(), mapper = get() ) }
    factory { PlaylistsPresenter( getPlaylists = get(), mapper = get() ) }
    factory {
        TvChannelsPresenter(
            getPagedTvChannels = get(),
            getCurrentTvGuide = get(),
            mapper = get()
        )
    }

    /* View Models */
    viewModel { ChannelsAvailabilityViewModel( presenter = get() ) }
    viewModel { (playlistPath: String?) ->
        EditPlaylistViewModel(
            interactor = get(),
            presenter = get(),
            playlistPath = playlistPath
        )
    }
    viewModel { PlaylistsViewModel( presenter = get() ) }
    viewModel { (groupName: String?) ->
        TvChannelsViewModel(
            presenter = get(),
            interactor = get(),
            groupName = groupName
        )
    }
}