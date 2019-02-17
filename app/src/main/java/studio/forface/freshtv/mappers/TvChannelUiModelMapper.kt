package studio.forface.freshtv.mappers

import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.uimodels.TvChannelUiModel

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
        val currentProgram = guide?.let { null } // TODO
        val imageUrl = channel.imageUrl?.s ?: EMPTY_STRING // TODO
        return TvChannelUiModel( channel.id, channel.name, imageUrl, currentProgram )
    }

    /** @see UiModelMapper.toEntity */
    override fun TvChannelUiModel.toEntity() { /* Unsupported */ }
}

/** A typealias of a [Pair] of [TvChannel] and OPTIONAL [TvGuide] */
private typealias TvChannelWithGuide = Pair<TvChannel, TvGuide?>

/** @return [TvChannel] from a [TvChannelWithGuide] */
private val TvChannelWithGuide.channel get() = first

/** @return OPTIONAL [TvGuide] from a [TvChannelWithGuide] */
private val TvChannelWithGuide.guide get() = second