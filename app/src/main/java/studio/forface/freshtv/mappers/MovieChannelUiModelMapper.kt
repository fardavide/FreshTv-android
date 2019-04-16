package studio.forface.freshtv.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.uimodels.MovieChannelUiModel
import studio.forface.freshtv.R.drawable.ic_tv as tvDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite as favoriteDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite_black as notFavoriteDrawable

/**
 * @author Davide Giuseppe Farella.
 * A Mapper of [MovieChannelUiModel]
 *
 * Inherit from [UiModelMapper]
 */
internal class MovieChannelUiModelMapper :
        UiModelMapper<MovieChannel, MovieChannelUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun MovieChannel.toUiModel(): MovieChannelUiModel {
        val image = imageUrl?.s
        val favoriteImage = if ( favorite ) favoriteDrawable else notFavoriteDrawable

        return MovieChannelUiModel(
            id =                    id,
            name =                  name,
            description =           null,
            image =                 image,
            imagePlaceHolder =      tvDrawable,
            favorite =              favorite,
            favoriteImage =         favoriteImage,
            favoriteImageNeedTint = ! favorite
        )
    }

    /** @see UiModelMapper.toEntity */
    override fun MovieChannelUiModel.toEntity() = unsupported
}