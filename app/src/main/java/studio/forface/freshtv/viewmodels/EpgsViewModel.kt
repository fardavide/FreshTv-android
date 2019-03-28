package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.presenters.EpgsPresenter
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.viewstatestore.paging.LockedPagedViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `EPG`s
 *
 * Inherit from [ScopedViewModel]
 */
internal class EpgsViewModel(
        private val presenter: EpgsPresenter
): ScopedViewModel() {

    /** A [LockedPagedViewStateStore] of [SourceFileUiModel] */
    val epgs = PagedViewStateStore<SourceFileUiModel>(50 ).lock

    init {
        epgs.setLoading()
        runCatching { presenter() }
                .onSuccess { epgs.setDataSource( it ) }
                .onFailure { epgs.setError( it ) }
    }
}