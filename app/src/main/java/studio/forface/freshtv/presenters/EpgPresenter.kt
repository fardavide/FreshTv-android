package studio.forface.freshtv.presenters

import studio.forface.freshtv.domain.usecases.GetEpg
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.mappers.invoke
import studio.forface.freshtv.uimodels.SourceFileUiModel.Epg

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get an `EPG`
 */
internal class EpgPresenter(
        private val getEpg: GetEpg,
        private val mapper: SourceFileUiModelMapper
) {

    /** @return [Epg] with the given [epgPath] */
    operator fun invoke( epgPath: String ) =
            mapper { getEpg( epgPath ).toUiModel() }
}