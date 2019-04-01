package studio.forface.freshtv.player

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.player.interactors.UpdateChannelSourceFailureInteractor
import studio.forface.freshtv.player.mappers.*
import studio.forface.freshtv.player.presenters.ChannelPresenter
import studio.forface.freshtv.player.presenters.ChannelSourcePresenter
import studio.forface.freshtv.player.presenters.TvGuidePresenter
import studio.forface.freshtv.player.presenters.TvGuidesPresenter
import studio.forface.freshtv.player.viewmodels.ChannelSourceViewModel
import studio.forface.freshtv.player.viewmodels.VideoPlayerViewModel

/** A [Module] that handles dependencies for use cases of `player` module */
val playerModule = module {

    /* Interactors */
    factory { UpdateChannelSourceFailureInteractor( incrementFailure = get(), resetFailure = get() ) }

    /* Mappers */
    factory { ChannelUiModelMapper() }
    factory { ChannelSourceUiModelMapper() }
    factory { MovieChannelInfoUiModelMapper() }
    factory { TvChannelInfoProgramUiModelMapper() }
    factory { TvChannelInfoUiModelMapper( programMapper = get() ) }

    /* Presenters */
    factory { ChannelPresenter( getChannel = get(), mapper = get() ) }
    factory { ChannelSourcePresenter( getChannel = get(), mapper = get() ) }
    factory { TvGuidePresenter( getTvGuide = get(), mapper = get() ) }
    factory { TvGuidesPresenter( getTvGuides = get(), mapper = get() ) }

    /* View Models */
    viewModel { (channelId: String) -> ChannelSourceViewModel( channelId, presenter = get(), interactor = get() ) }
    viewModel { VideoPlayerViewModel( androidApplication() ) }
}