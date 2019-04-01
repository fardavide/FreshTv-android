package studio.forface.freshtv.player.presenters

import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.domain.usecases.GetTvGuides
import studio.forface.freshtv.player.mappers.TvChannelInfoUiModelMapper
import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel

/**
 * A Presenter for get `TvGuide`s
 *
 * @author Davide Giuseppe Farella
 */
internal class TvGuidesPresenter(
        private val getTvGuides: GetTvGuides,
        private val mapper: TvChannelInfoUiModelMapper
) {

    /** @return [ChannelInfoUiModel.Tv] for the given [id] */
    operator fun invoke( id: String ) = mapper { getTvGuides.invoke( id ).toUiModel() }
}