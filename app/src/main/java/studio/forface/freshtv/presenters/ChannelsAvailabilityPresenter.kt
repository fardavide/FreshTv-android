package studio.forface.freshtv.presenters

import studio.forface.freshtv.domain.usecases.HasMovieChannels
import studio.forface.freshtv.domain.usecases.HasTvChannels
import studio.forface.freshtv.uimodels.ChannelsAvailabilityUiModel

/**
 * @author Davide Giuseppe Farella
 * A Presenter for check the availability of Channels
 */
internal class ChannelsAvailabilityPresenter(
    private val hasMovieChannels: HasMovieChannels,
    private val hasTvChannels: HasTvChannels
) {
    /** @return [ChannelsAvailabilityUiModel] */
    operator fun invoke() = ChannelsAvailabilityUiModel( hasMovies = hasMovieChannels(), hasTvs = hasTvChannels() )
}