package studio.forface.freshtv.player

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.player.interactors.UpdateChannelSourceFailureInteractor
import studio.forface.freshtv.player.mappers.*
import studio.forface.freshtv.player.presenters.*
import studio.forface.freshtv.player.viewmodels.*

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
    factory { TvProgramUiModelMapper() }

    /* Presenters */
    factory { ChannelPresenter( getChannel = get(), mapper = get() ) }
    factory { ChannelTypePresenter( getChannel = get() ) }
    factory { ChannelSourcePresenter( getChannel = get(), mapper = get() ) }
    factory { TvGuidePresenter( getTvGuide = get(), mapper = get() ) }
    factory { TvGuidesPresenter( getTvGuides = get(), mapper = get() ) }

    /* View Models */
    viewModel { (channelId: String) -> ChannelInfoViewModel( channelId, typePresenter = get(), tvPresenter = get() ) }
    viewModel { (channelId: String) -> ChannelTypeViewModel( channelId, presenter = get() ) }
    viewModel { (channelId: String) -> ChannelSourceViewModel( channelId, presenter = get(), interactor = get() ) }
    viewModel { (guideId: String) -> TvGuideViewModel( guideId, presenter = get() ) }
    viewModel { VideoPlayerViewModel( androidApplication() ) }
}