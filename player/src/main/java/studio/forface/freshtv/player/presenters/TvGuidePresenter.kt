package studio.forface.freshtv.player.presenters

import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.domain.usecases.GetTvGuide
import studio.forface.freshtv.player.mappers.TvProgramUiModelMapper
import studio.forface.freshtv.player.uiModels.TvProgramUiModel

/**
 * A Presenter for get a `TvGuide`
 *
 * @author Davide Giuseppe Farella
 */
internal class TvGuidePresenter(
        private val getTvGuide: GetTvGuide,
        private val mapper: TvProgramUiModelMapper
) {

    /** @return [TvProgramUiModel] for the given [id] */
    operator fun invoke( id: String ) = mapper { getTvGuide.invoke( id ).toUiModel() }
}