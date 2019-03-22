package studio.forface.freshtv.player.presenters

import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.usecases.GetChannel
import studio.forface.freshtv.player.mappers.ChannelSourceUiModelMapper
import studio.forface.freshtv.player.uiModels.ChannelSourceUiModel

/**
 * A Presenter for get a `Source` for a `Channel`
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelSourcePresenter(
        private val getChannel: GetChannel,
        private val mapper: ChannelSourceUiModelMapper
) {

    /** @return [ReceiveChannel] of [ChannelSourceUiModel] for the given [id] */
    suspend operator fun invoke( id: String ) = getChannel.observe( id ).map( mapper ) { it.toUiModel() }
}