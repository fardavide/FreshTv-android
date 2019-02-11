package studio.forface.freshtv

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.presenters.ChannelsAvailabilityPresenter
import studio.forface.freshtv.viewmodels.ChannelsAvailabilityViewModel

/** A [Module] that handles dependencies for use cases of `app` module */
val appModule = module {

    /* Presenters */
    factory { ChannelsAvailabilityPresenter( hasMovieChannels = get(), hasTvChannels = get() ) }

    /* View Models */
    viewModel { ChannelsAvailabilityViewModel( presenter = get() ) }
}