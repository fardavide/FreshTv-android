package studio.forface.freshtv.player.presenters

import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.domain.usecases.GetTvGuide
import studio.forface.freshtv.domain.usecases.GetTvGuides
import studio.forface.freshtv.player.mappers.TvChannelInfoProgramUiModelMapper
import studio.forface.freshtv.player.mappers.TvChannelInfoUiModelMapper
import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel

/**
 * A Presenter for get a `TvGuide`
 *
 * @author Davide Giuseppe Farella
 */
internal class TvGuidePresenter(
        private val getTvGuide: GetTvGuide,
        private val mapper: TvChannelInfoProgramUiModelMapper
) {

    /** @return [ChannelInfoUiModel.Tv.Program] for the given [id] */
    operator fun invoke( id: String ) = mapper { getTvGuide.invoke( id ).toUiModel() }
}