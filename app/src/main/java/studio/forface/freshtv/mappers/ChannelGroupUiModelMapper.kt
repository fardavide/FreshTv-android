package studio.forface.freshtv.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.uimodels.ChannelGroupUiModel
import studio.forface.freshtv.R.drawable.ic_tv as tvDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite as favoriteDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite_black as notFavoriteDrawable

/**
 * @author Davide Giuseppe Farella.
 * A Mapper of [ChannelGroupUiModel]
 *
 * Inherit from [UiModelMapper]
 */
internal class ChannelGroupUiModelMapper : UiModelMapper<ChannelGroup, ChannelGroupUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun ChannelGroup.toUiModel() = ChannelGroupUiModel( name )

    /** @see UiModelMapper.toEntity */
    override fun ChannelGroupUiModel.toEntity() = unsupported
}