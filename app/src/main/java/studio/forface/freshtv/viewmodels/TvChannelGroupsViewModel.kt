package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.presenters.TvChannelGroupsPresenter
import studio.forface.freshtv.uimodels.TvChannelGroupUiModel
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.setData
import studio.forface.viewstatestore.setError
import studio.forface.viewstatestore.setLoading

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `TvChannel`s Groups
 *
 * Inherit from [ScopedViewModel]
 */
internal class TvChannelGroupsViewModel(
        private val presenter: TvChannelGroupsPresenter
): ScopedViewModel() {

    /** A [ViewStateStore] of [List] of [TvChannelGroupUiModel] */
    val groups = ViewStateStore<List<TvChannelGroupUiModel>>()

    init {
        groups.setLoading()
        runCatching { presenter() }
                .onSuccess { groups.setData( it ) }
                .onFailure { groups.setError( it ) }
    }
}