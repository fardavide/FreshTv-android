package studio.forface.freshtv.player.presenters

import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.usecases.GetChannel
import studio.forface.freshtv.player.mappers.ChannelUiModelMapper
import studio.forface.freshtv.player.uiModels.ChannelUiModel

/**
 * A Presenter for get a `Channel`
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelPresenter(
        private val getChannel: GetChannel,
        private val mapper: ChannelUiModelMapper
) {

    /** @return [ChannelUiModel] for the given [id] */
    suspend operator fun invoke( id: String ) = getChannel.observe( id ).map( mapper ) { it.toUiModel() }
}