package studio.forface.freshtv.presenters

import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * A Presenter for get a Source File
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class AbsSourceFilePresenter {

    /** A [SourceFileUiModelMapper] for map the entity to [SourceFileUiModel] */
    protected abstract val mapper: SourceFileUiModelMapper

    /** @return [SourceFileUiModel] with the given [filePath] */
    abstract operator fun invoke( filePath: String ): SourceFileUiModel
}