package studio.forface.freshtv.preferences.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.domain.usecases.HasMovieChannels
import studio.forface.freshtv.domain.usecases.HasTvChannels
import studio.forface.freshtv.domain.utils.invoke
import studio.forface.freshtv.preferences.mappers.ChannelsDatabaseStateUiModelMapper
import studio.forface.freshtv.preferences.uimodels.ChannelsDatabaseStateUiModel

/**
 * A Presenter for get Preferences for Clean Channels
 *
 * @author Davide Giuseppe Farella
 */
internal interface CleanChannelsPresenter {

    /** @return [ChannelsDatabaseStateUiModel] */
    fun channelsDatabaseState(): ChannelsDatabaseStateUiModel

    /** @return [ReceiveChannel] of [ChannelsDatabaseStateUiModel] */
    fun CoroutineScope.observeChannelsDatabaseState() : ReceiveChannel<ChannelsDatabaseStateUiModel>
}

/** Implementation of [CleanChannelsPresenter] */
internal class CleanChannelsPresenterImpl(
    private val hasMovieChannels: HasMovieChannels,
    private val hasTvChannels: HasTvChannels,
    private val mapper: ChannelsDatabaseStateUiModelMapper
) : CleanChannelsPresenter {

    private var hadMovieChannels: Boolean? = null
    private var hadTvChannels: Boolean? = null

    /** @return [ChannelsDatabaseStateUiModel] */
    override fun channelsDatabaseState(): ChannelsDatabaseStateUiModel {
        hadMovieChannels = hasMovieChannels()
        hadTvChannels = hasTvChannels()

        return assertMakeModel()
    }

    /**
     * @return [ReceiveChannel] of [ChannelsDatabaseStateUiModel]
     * Creates a single [ReceiveChannel] from [HasMovieChannels.observe] and [HasTvChannels.observe]
     * [ReceiveChannel]s
     */
    override fun CoroutineScope.observeChannelsDatabaseState(): ReceiveChannel<ChannelsDatabaseStateUiModel> {
        val channel = Channel<ChannelsDatabaseStateUiModel>( Channel.CONFLATED )

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

    /** @return OPTIONAL [ChannelsDatabaseStateUiModel] IF [hadMovieChannels] and [hadTvChannels] are NOT NULL */
    private fun maybeMakeModel() = ( hadMovieChannels != null && hadTvChannels != null ) { assertMakeModel() }

    /** @return [ChannelsDatabaseStateUiModel], asserts that [hadMovieChannels] and [hadTvChannels] are NOT NULL */
    private fun assertMakeModel() = mapper.invoke { ( hadMovieChannels!! || hadTvChannels!! ).toUiModel() }
}