package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.presenters.EpgsPresenter
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore
import studio.forface.viewstatestore.setError
import studio.forface.viewstatestore.setLoading

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `EPG`s
 *
 * Inherit from [ScopedViewModel]
 */
internal class EpgsViewModel(
        private val presenter: EpgsPresenter
): ScopedViewModel() {

    /** A [ViewStateStore] of [PagedList] of [SourceFileUiModel] */
    val epgs = PagedViewStateStore<SourceFileUiModel>(50 )

    init {
        epgs.setLoading()
        runCatching { presenter() }
                .onSuccess { epgs.setDataSource( it ) }
                .onFailure { epgs.setError( it ) }
    }
}