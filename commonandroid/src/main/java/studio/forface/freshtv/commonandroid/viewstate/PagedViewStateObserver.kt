package studio.forface.freshtv.commonandroid.viewstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

/**
 * @author Davide Giuseppe Farella.
 * An observer for [ViewState] that observes a `LiveData` of [V] created by a [DataSource.Factory]
 * of [V] and deliver results to itself
 */
class PagedViewStateObserver<V>(
        factory: DataSource.Factory<Int, V>,
        pageSize: Int,
        owner: LifecycleOwner?
): ViewStateObserver<PagedList<V>>() {

    /** A `LiveData` created by [DataSource.Factory] */
    private val liveData = LivePagedListBuilder( factory, pageSize ).build()

    init {
        val observer = Observer<PagedList<V>> { super.onData( it ) }
        owner?.let { liveData.observe( it, observer ) } ?: liveData.observeForever( observer )
    }
}