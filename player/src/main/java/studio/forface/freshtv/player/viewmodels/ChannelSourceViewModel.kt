package studio.forface.freshtv.player.viewmodels

import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.player.interactors.IncrementChannelSourceFailureInteractor
import studio.forface.freshtv.player.presenters.ChannelSourcePresenter

/**
 * A View Model for get a `Source` for a `Channel` and increment it in case of error.
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelSourceViewModel(
        private val channelId: String,
        private val presenter: ChannelSourcePresenter,
        private val interactor: IncrementChannelSourceFailureInteractor
) : ScopedViewModel()