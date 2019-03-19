package studio.forface.freshtv

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.interactors.EditEpgInteractor
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.mappers.TvChannelGroupUiModelMapper
import studio.forface.freshtv.mappers.TvChannelUiModelMapper
import studio.forface.freshtv.presenters.*
import studio.forface.freshtv.viewmodels.*

/** A [Module] that handles dependencies for use cases of `app` module */
val appModule = module {

    /* Interactors */
    factory { ChannelChangeFavoriteInteractor( updateChannelFavoriteState = get() ) }
    factory { EditEpgInteractor( addEpg = get(), removeEpg = get(), updateEpg = get() ) }
    factory { EditPlaylistInteractor( addPlaylist = get(), updatePlaylist = get() ) }

    /* Mappers */
    factory { SourceFileUiModelMapper() }
    factory { TvChannelGroupUiModelMapper() }
    factory { TvChannelUiModelMapper() }

    /* Presenters */
    factory { ChannelsAvailabilityPresenter( hasMovieChannels = get(), hasTvChannels = get() ) }
    factory { EpgPresenter( getEpg = get(), mapper = get() ) }
    factory { EpgsPresenter( getEpgs = get(), mapper = get() ) }
    factory { PlaylistPresenter( getPlaylist = get(), mapper = get() ) }
    factory { PlaylistsPresenter( getPlaylists = get(), mapper = get() ) }
    factory { TvChannelGroupsPresenter( getGroups = get(), mapper = get() ) }
    factory { TvChannelsPresenter( getPagedTvChannels = get(), mapper = get() ) }

    /* View Models */
    viewModel { ChannelsAvailabilityViewModel( presenter = get() ) }
    viewModel { (epgPath: String?) -> EditEpgViewModel( interactor = get(), presenter = get(), epgPath = epgPath ) }
    viewModel { (playlistPath: String?) -> EditPlaylistViewModel( interactor = get(),  presenter = get(),  playlistPath = playlistPath ) }
    viewModel { EpgsViewModel( presenter = get() ) }
    viewModel { PlaylistsViewModel( presenter = get() ) }
    viewModel { TvChannelGroupsViewModel( presenter = get() ) }
    viewModel { (groupName: String) -> TvChannelsViewModel( presenter = get(),  interactor = get(),  groupName = groupName ) }
}