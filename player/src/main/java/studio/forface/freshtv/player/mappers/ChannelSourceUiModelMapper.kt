package studio.forface.freshtv.player.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.player.uiModels.ChannelSourceUiModel

/**
 * A Mapper of [ChannelSourceUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelSourceUiModelMapper : UiModelMapper<IChannel, ChannelSourceUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun IChannel.toUiModel(): ChannelSourceUiModel {
        val url = mediaUrls.toList()
            .sortedBy { it.second }
            .first()
            .first
        return ChannelSourceUiModel( url )
    }

    /** @see UiModelMapper.toEntity */
    override fun ChannelSourceUiModel.toEntity() = unsupported
}