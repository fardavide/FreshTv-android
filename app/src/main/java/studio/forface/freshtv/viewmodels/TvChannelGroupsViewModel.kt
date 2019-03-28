package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.presenters.TvChannelGroupsPresenter
import studio.forface.freshtv.uimodels.TvChannelGroupUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `TvChannel`s Groups
 *
 * Inherit from [ScopedViewModel]
 */
internal class TvChannelGroupsViewModel(
        private val presenter: TvChannelGroupsPresenter
): ScopedViewModel() {

    /** A [LockedViewStateStore] of [List] of [TvChannelGroupUiModel] */
    val groups = ViewStateStore<List<TvChannelGroupUiModel>>().lock

    init {
        groups.setLoading()
        runCatching { presenter() }
                .onSuccess { groups.setData( it ) }
                .onFailure { groups.setError( it ) }
    }
}