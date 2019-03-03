package studio.forface.freshtv.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.utils.invoke
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
    private var hadMovieChannels: Boolean? = null
    private var hadTvChannels: Boolean? = null

    /** @return [ChannelsAvailabilityUiModel] */
    operator fun invoke(): ChannelsAvailabilityUiModel {
        hadMovieChannels = hasMovieChannels()
        hadTvChannels = hasTvChannels()

        return assertMakeModel()
    }

    /**
     * @return [ReceiveChannel] of [ChannelsAvailabilityUiModel]
     * Creates a single [ReceiveChannel] from [HasMovieChannels.observe] and [HasTvChannels.observe]
     * [ReceiveChannel]s
     */
    fun CoroutineScope.observe(): ReceiveChannel<ChannelsAvailabilityUiModel> {
        val channel = Channel<ChannelsAvailabilityUiModel>( CONFLATED )

        launch( IO ) {
            for ( boolean in hasMovieChannels.observe() ) {
                // Cache the value
                hadMovieChannels = boolean
                // Create and send UiModel if both hadMovieChannels and hadTvChannels are not null
                maybeMakeModel()?.let { channel.offer( it ) }
            }
        }

        launch( IO ) {
            for ( boolean in hasTvChannels.observe() ) {
                // Cache the value
                hadTvChannels = boolean
                // Create and send UiModel if both hadMovieChannels and hadTvChannels are not null
                maybeMakeModel()?.let { channel.offer( it ) }
            }
        }

        return channel
    }

    /** @return OPTIONAL [ChannelsAvailabilityUiModel] IF [hadMovieChannels] and [hadTvChannels] are NOT NULL */
    private fun maybeMakeModel() = ( hadMovieChannels != null && hadTvChannels != null ) { assertMakeModel() }

    /** @return [ChannelsAvailabilityUiModel], asserts that [hadMovieChannels] and [hadTvChannels] are NOT NULL */
    private fun assertMakeModel() = ChannelsAvailabilityUiModel(
        hasMovies = hadMovieChannels!!,
        hasTvs = hadTvChannels!!
    )
}

internal suspend operator fun <T> ChannelsAvailabilityPresenter.invoke(
    block: suspend ChannelsAvailabilityPresenter.() -> T
) = block()