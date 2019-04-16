package studio.forface.freshtv.mappers

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.commonandroid.utils.defaultTimeFormatter
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.entities.TvChannelWithGuide
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.domain.utils.LocalDateTimeHelper.localOffset
import studio.forface.freshtv.domain.utils.toEpochMillis
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.freshtv.R.drawable.ic_tv as tvDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite as favoriteDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite_black as notFavoriteDrawable

/**
 * @author Davide Giuseppe Farella.
 * A Mapper of [TvChannelUiModel]
 *
 * Inherit from [UiModelMapper]
 */
internal class TvChannelUiModelMapper :
        UiModelMapper<TvChannelWithGuide, TvChannelUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun TvChannelWithGuide.toUiModel(): TvChannelUiModel {
        val image = imageUrl?.s
        val favoriteImage = if ( favorite ) favoriteDrawable else notFavoriteDrawable
        val currentProgram = program?.toUiModel()

        return TvChannelUiModel(
            id =                    id,
            name =                  name,
            image =                 image,
            imagePlaceHolder =      tvDrawable,
            favorite =              favorite,
            favoriteImage =         favoriteImage,
            favoriteImageNeedTint = !favorite,
            currentProgram =        currentProgram
        )
    }

    private fun TvChannelWithGuide.Program.toUiModel(): TvChannelUiModel.CurrentProgram {
        val ( startEpoch, endEpoch ) = startTime.toEpochMillis( localOffset ) to endTime.toEpochMillis( localOffset )
        val currentEpoch = LocalDateTime.now().toEpochMillis( localOffset )

        val runtime = endEpoch - startEpoch
        val progress = currentEpoch - startEpoch
        // runtime : progress = 100 : x
        val progressPercentage = ( progress * 100 ) / runtime

        return TvChannelUiModel.CurrentProgram(
            title =                 title,
            startTime =             startTime.format( defaultTimeFormatter ),
            endTime =               endTime.format( defaultTimeFormatter ),
            progressPercentage =    progressPercentage.toInt()
        )
    }

    /** @see UiModelMapper.toEntity */
    override fun TvChannelUiModel.toEntity() = unsupported
}