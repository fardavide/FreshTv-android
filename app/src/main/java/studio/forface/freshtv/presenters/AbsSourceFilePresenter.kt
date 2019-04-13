package studio.forface.freshtv.presenters

import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * A Presenter for get a Source File
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class AbsSourceFilePresenter {

    /** @return [SourceFileUiModel] with the given [filePath] */
    abstract operator fun invoke( filePath: String ): SourceFileUiModel
}