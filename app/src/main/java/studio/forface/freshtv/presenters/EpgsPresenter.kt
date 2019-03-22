package studio.forface.freshtv.presenters

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.usecases.GetPagedEpgs
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get all the stored `EPG`s
 */
internal class EpgsPresenter(
    private val getEpgs: GetPagedEpgs,
    private val mapper: SourceFileUiModelMapper
) {

    /** @return a [DataSource.Factory] of [SourceFileUiModel] */
    operator fun invoke() = getEpgs().map( mapper ) { it.toUiModel() }
}