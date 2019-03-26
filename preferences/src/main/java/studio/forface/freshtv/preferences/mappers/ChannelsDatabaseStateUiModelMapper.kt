package studio.forface.freshtv.preferences.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.preferences.uimodels.ChannelsDatabaseStateUiModel

/**
 * A Mapper for [ChannelsDatabaseStateUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelsDatabaseStateUiModelMapper: UiModelMapper<Boolean, ChannelsDatabaseStateUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun Boolean.toUiModel() =
            if ( this ) ChannelsDatabaseStateUiModel.NotEmpty else ChannelsDatabaseStateUiModel.Empty

    /** @see UiModelMapper.toEntity */
    override fun ChannelsDatabaseStateUiModel.toEntity() = unsupported
}