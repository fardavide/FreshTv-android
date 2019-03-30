package studio.forface.freshtv.player.uiModels

import studio.forface.freshtv.domain.entities.IChannel

/**
 * An Ui Model representing a source url for `Channel`
 *
 * @author Davide Giuseppe Farella
 */
internal data class ChannelSourceUiModel(
    val url: String,
    val type: IChannel.Type
)