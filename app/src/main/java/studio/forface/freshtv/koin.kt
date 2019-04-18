package studio.forface.freshtv

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.interactors.*
import studio.forface.freshtv.interactors.EditEpgInteractor
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.interactors.SaveLastMovieChannelInteractor
import studio.forface.freshtv.interactors.SaveLastMovieGroupInteractor
import studio.forface.freshtv.interactors.SaveLastTvGroupInteractor
import studio.forface.freshtv.mappers.*
import studio.forface.freshtv.presenters.*
import studio.forface.freshtv.viewmodels.*

/** A [Module] that handles dependencies of `app` module */
val appModule = module {

    /* Interactors */
    factory { EditEpgInteractor( addEpg = get(), removeEpg = get(), updateEpg = get() ) }
    factory { EditPlaylistInteractor( addPlaylist = get(), updatePlaylist = get() ) }
    factory { SaveLastMovieChannelInteractor( saveLastMovieChannelIdAndPosition = get() ) }
    factory { SaveLastMovieGroupInteractor( saveLastMovieChannelGroupName = get() ) }
    factory { SaveLastTvChannelInteractor( saveLastTvChannelIdAndPosition = get() ) }
    factory { SaveLastTvGroupInteractor( saveLastTvChannelGroupName = get() ) }

    /* Mappers */
    factory { ChannelGroupUiModelMapper() }
    factory { ChannelGroupsUiModelMapper( subMapper = get() ) }
    factory { MovieChannelUiModelMapper() }
    factory { SourceFileUiModelMapper() }
    factory { TvChannelUiModelMapper() }

    /* Presenters */
    factory { ChannelsAvailabilityPresenter( hasMovieChannels = get(), hasTvChannels = get() ) }
    factory { EpgPresenter( getEpg = get(), mapper = get() ) }
    factory { EpgsPresenter( getEpgs = get(), mapper = get() ) }
    factory { MovieChannelsPresenter( getPagedMovieChannels = get(), mapper = get(), getLastMovieChannelIdAndPosition = get() ) }
    factory { MovieChannelGroupsPresenter( getMovieGroups = get(), getLastMovieChannelGroupName = get(), mapper = get() ) }
    factory { PlaylistPresenter( getPlaylist = get(), mapper = get() ) }
    factory { PlaylistsPresenter( getPlaylists = get(), mapper = get() ) }
    factory { TvChannelsPresenter( getPagedTvChannels = get(), mapper = get(), getLastTvChannelIdAndPosition = get() ) }
    factory { TvChannelGroupsPresenter( getTvGroups = get(), getLastTvChannelGroupName = get(), mapper = get() ) }

    /* View Models */
    viewModel { ChannelsAvailabilityViewModel( presenter = get() ) }
    viewModel { (epgPath: String?) -> EditEpgViewModel( epgPath, presenter = get(), interactor = get() ) }
    viewModel { (playlistPath: String?) -> EditPlaylistViewModel( playlistPath, presenter = get(), interactor = get() ) }
    viewModel { EpgsViewModel( presenter = get() ) }
    viewModel { (groupName: String) -> MovieChannelsViewModel(
        groupName,
        presenter = get(),
        favoriteInteractor = get(),
        lastChannelInteractor = get()
    ) }
    viewModel { MovieChannelGroupsViewModel( presenter = get(), lastGroupInteractor = get() ) }
    viewModel { PlaylistsViewModel( presenter = get() ) }
    viewModel { TvChannelGroupsViewModel( presenter = get(), lastGroupInteractor = get() ) }
    viewModel { (groupName: String) -> TvChannelsViewModel(
        groupName,
        presenter = get(),
        favoriteInteractor = get(),
        lastChannelInteractor = get()
    ) }
}