package studio.forface.freshtv.mappers

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.commonandroid.utils.defaultTimeFormatter
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.utils.LocalDateTimeHelper.localOffset
import studio.forface.freshtv.domain.utils.toEpochMillis
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite as favoriteDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite_black as notFavoriteDrawable
import studio.forface.freshtv.R.drawable.ic_tv as tvDrawable

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
        val image = channel.imageUrl?.s
        val favoriteImage = if ( channel.favorite ) favoriteDrawable else notFavoriteDrawable
        val currentProgram = guide?.toUiModel()

        return TvChannelUiModel(
            id =                channel.id,
            name =              channel.name,
            image =             image,
            imagePlaceHolder =  tvDrawable,
            favorite =          channel.favorite,
            favoriteImage =     favoriteImage,
            currentProgram =    currentProgram
        )
    }

    private fun TvGuide.toUiModel(): TvChannelUiModel.CurrentProgram {
        val ( startEpoch, endEpoch ) = endTime.toEpochMillis( localOffset ) to startTime.toEpochMillis( localOffset )
        val currentEpoch = LocalDateTime.now().toEpochMillis( localOffset )

        val runtime = endEpoch - startEpoch
        val progress = currentEpoch - startEpoch
        // x : 100 = progress : runtime
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

/** A typealias of a [Pair] of [TvChannel] and OPTIONAL [TvGuide] */
private typealias TvChannelWithGuide = Pair<TvChannel, TvGuide?>

/** @return [TvChannel] from a [TvChannelWithGuide] */
private val TvChannelWithGuide.channel get() = first

/** @return OPTIONAL [TvGuide] from a [TvChannelWithGuide] */
private val TvChannelWithGuide.guide get() = second