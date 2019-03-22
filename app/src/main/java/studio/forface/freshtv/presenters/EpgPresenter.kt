package studio.forface.freshtv.presenters

import studio.forface.freshtv.domain.usecases.GetEpg
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.uimodels.SourceFileUiModel.Epg

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get an `EPG`
 *
 * Inherit from [AbsSourceFilePresenter]
 */
internal class EpgPresenter(
        private val getEpg: GetEpg,
        override val mapper: SourceFileUiModelMapper
) : AbsSourceFilePresenter() {

    /** @return [Epg] with the given [filePath] */
    override operator fun invoke( filePath: String ) =
            mapper { getEpg( filePath ).toUiModel() }
}