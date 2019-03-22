package studio.forface.freshtv.player.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.commonandroid.mappers.Unsupported
import studio.forface.freshtv.commonandroid.mappers.unsupported
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.uiModels.ChannelSourceUiModel
import studio.forface.freshtv.player.uiModels.ChannelUiModel

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