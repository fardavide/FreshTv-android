package studio.forface.freshtv.player.mappers

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.commonandroid.utils.defaultDateTimeFormatter
import studio.forface.freshtv.commonandroid.utils.joinToString
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel
import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel.Tv.Program.OptInfo

/**
 * A Mapper of [ChannelInfoUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella.
 */
internal abstract class ChannelInfoUiModelMapper<in Ein, UI> : UiModelMapper<Ein, UI, Unsupported>() {

    /** @see UiModelMapper.toEntity */
    override fun UI.toEntity() = unsupported
}

/**
 * A Mapper of [ChannelInfoUiModel.Movie]
 * Inherit from [ChannelInfoUiModelMapper]
 *
 * @author Davide Giuseppe Farella.
 */
internal class MovieChannelInfoUiModelMapper : ChannelInfoUiModelMapper<Nothing, ChannelInfoUiModel.Movie>() {

    /** @see UiModelMapper.toUiModel */
    override fun Nothing.toUiModel(): ChannelInfoUiModel.Movie {
        TODO("not implemented" )
    }
}

/**
 * A Mapper of [ChannelInfoUiModel.Tv]
 * Inherit from [ChannelInfoUiModelMapper]
 *
 * @author Davide Giuseppe Farella.
 */
internal class TvChannelInfoUiModelMapper(
        private val programMapper: TvChannelInfoProgramUiModelMapper
) : ChannelInfoUiModelMapper<List<TvGuide>, ChannelInfoUiModel.Tv>() {

    /** @see UiModelMapper.toUiModel */
    override fun List<TvGuide>.toUiModel(): ChannelInfoUiModel.Tv {
        val sortedGuides = sortedBy { it.startTime }
        val currentIndex = sortedGuides.indexOfFirst { it.endTime > LocalDateTime.now() }
        return ChannelInfoUiModel.Tv(
                programs = sortedGuides.map( programMapper ) { it.toUiModel() },
                currentIndex = currentIndex
        )
    }
}

/**
 * A Mapper of [ChannelInfoUiModel.Tv.Program]
 * Inherit from [ChannelInfoUiModelMapper]
 *
 * @author Davide Giuseppe Farella.
 */
internal class TvChannelInfoProgramUiModelMapper : ChannelInfoUiModelMapper<TvGuide, ChannelInfoUiModel.Tv.Program>() {

    /** @see UiModelMapper.toUiModel */
    override fun TvGuide.toUiModel(): ChannelInfoUiModel.Tv.Program {
        val optionalInformations = ChannelInfoUiModel.Tv.Program.OptionalInformations(
            category =  category    ?.let { OptInfo( R.string.category, it              ) },
            year =      year        ?.let { OptInfo( R.string.year,     it.toString()   ) },
            country =   country     ?.let { OptInfo( R.string.country,  it              ) },
            rating =    rating      ?.let { OptInfo( R.string.rating,   it              ) }
        )
        return ChannelInfoUiModel.Tv.Program (
            title =                 title,
            description =           description,
            image =                 imageUrl?.s,
            optionalInformations =  optionalInformations,
            director =              credits?.director,
            actors =                credits?.actors?.joinToString(),
            startTime =             startTime.format( defaultDateTimeFormatter ),
            endTime =               endTime.format( defaultDateTimeFormatter )
        )
    }
}