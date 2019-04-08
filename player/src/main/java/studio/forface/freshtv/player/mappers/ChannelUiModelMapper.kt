package studio.forface.freshtv.player.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.uiModels.ChannelUiModel

/**
 * A Mapper of [ChannelUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelUiModelMapper : UiModelMapper<IChannel, ChannelUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun IChannel.toUiModel() = ChannelUiModel(
            id =            id,
            title =         name,
            image =         imageUrl?.s,
            favorite =      favorite,
            favoriteImage = if ( favorite ) R.drawable.ic_favorite else R.drawable.ic_favorite_white,
            type =          type
    )

    /** @see UiModelMapper.toEntity */
    override fun ChannelUiModel.toEntity() = unsupported
}