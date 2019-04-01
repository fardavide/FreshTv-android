package studio.forface.freshtv.player.mappers

import org.threeten.bp.format.DateTimeFormatter
import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.commonandroid.utils.defaultDateTimeFormatter
import studio.forface.freshtv.commonandroid.utils.joinToString
import studio.forface.freshtv.commonandroid.utils.nullIfBlank
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.uiModels.TvProgramUiModel
import studio.forface.freshtv.player.uiModels.TvProgramUiModel.OptInfo

/**
 * A Mapper of [TvProgramUiModel]
 * Inherit from [ChannelInfoUiModelMapper]
 *
 * @author Davide Giuseppe Farella.
 */
internal class TvProgramUiModelMapper : ChannelInfoUiModelMapper<TvGuide, TvProgramUiModel>() {

    /** @see UiModelMapper.toUiModel */
    override fun TvGuide.toUiModel(): TvProgramUiModel {
        val optionalInformations = TvProgramUiModel.OptionalInformations(
            category =  category    ?.let { OptInfo( R.string.category, it              ) },
            year =      year        ?.let { OptInfo( R.string.year,     it.toString()   ) },
            country =   country     ?.let { OptInfo( R.string.country,  it              ) },
            rating =    rating      ?.let { OptInfo( R.string.rating,   it              ) }
        )
        return TvProgramUiModel (
            title =                 title,
            description =           description,
            image =                 imageUrl?.s,
            optionalInformations =  optionalInformations,
            director =              credits?.director.nullIfBlank(),
            actors =                credits?.actors?.joinToString().nullIfBlank()
        )
    }
}