package studio.forface.freshtv.player

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.player.interactors.UpdateChannelSourceFailureInteractor
import studio.forface.freshtv.player.mappers.*
import studio.forface.freshtv.player.presenters.ChannelPresenter
import studio.forface.freshtv.player.presenters.ChannelSourcePresenter
import studio.forface.freshtv.player.viewmodels.ChannelSourceViewModel

/** A [Module] that handles dependencies for use cases of `player` module */
val playerModule = module {

    /* Interactors */
    factory { UpdateChannelSourceFailureInteractor( incrementFailure = get(), resetChannelMediaFailure = get() ) }

    /* Mappers */
    factory { ChannelUiModelMapper() }
    factory { ChannelSourceUiModelMapper() }
    factory { MovieChannelInfoUiModelMapper() }
    factory { TvChannelInfoProgramUiModelMapper() }
    factory { TvChannelInfoUiModelMapper( programMapper = get() ) }

    /* Presenters */
    factory { ChannelPresenter( getChannel = get(), mapper = get() ) }
    factory { ChannelSourcePresenter( getChannel = get(), mapper = get() ) }

    /* View Models */
    viewModel { (channelId: String) -> ChannelSourceViewModel( channelId, presenter = get(), interactor = get() ) }
}