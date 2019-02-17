package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.commonandroid.viewstate.*
import studio.forface.freshtv.presenters.ChannelsAvailabilityPresenter
import studio.forface.freshtv.presenters.TvChannelsPresenter
import studio.forface.freshtv.uimodels.ChannelsAvailabilityUiModel
import studio.forface.freshtv.uimodels.TvChannelUiModel

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `TvChannel`s
 *
 * Inherit from [ScopedViewModel]
 */
internal class TvChannelsViewModel(
        private val presenter: TvChannelsPresenter,
        private val groupName: String?
): ScopedViewModel() {

    /** A [ViewStateStore] of [PagedList] of [TvChannelsViewModel] */
    val channels = PagedViewStateStore<TvChannelUiModel>(50 )

    init {
        channels.setLoading()
        runCatching { presenter( groupName ) }
                .onSuccess { channels.setDataSource( it ) }
                .onFailure { channels.setError( it ) }
    }
}