package studio.forface.freshtv.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.commonandroid.mappers.Unsupported
import studio.forface.freshtv.commonandroid.mappers.unsupported
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.uimodels.TvChannelGroupUiModel
import studio.forface.freshtv.R.drawable.ic_tv as tvDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite as favoriteDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite_black as notFavoriteDrawable

/**
 * @author Davide Giuseppe Farella.
 * A Mapper of [TvChannelGroupUiModel]
 *
 * Inherit from [UiModelMapper]
 */
internal class TvChannelGroupUiModelMapper : UiModelMapper<ChannelGroup, TvChannelGroupUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun ChannelGroup.toUiModel() = TvChannelGroupUiModel( name )

    /** @see UiModelMapper.toEntity */
    override fun TvChannelGroupUiModel.toEntity() = unsupported
}