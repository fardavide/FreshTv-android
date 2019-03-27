package studio.forface.freshtv.preferences.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.preferences.uimodels.GuidesDatabaseStateUiModel

/**
 * A Mapper for [GuidesDatabaseStateUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella
 */
internal class GuidesDatabaseStateUiModelMapper: UiModelMapper<Boolean, GuidesDatabaseStateUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun Boolean.toUiModel() =
            if ( this ) GuidesDatabaseStateUiModel.NotEmpty else GuidesDatabaseStateUiModel.Empty

    /** @see UiModelMapper.toEntity */
    override fun GuidesDatabaseStateUiModel.toEntity() = unsupported
}