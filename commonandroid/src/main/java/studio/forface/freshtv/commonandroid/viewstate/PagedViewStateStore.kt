@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package studio.forface.freshtv.commonandroid.viewstate

import androidx.annotation.UiThread
import androidx.lifecycle.LifecycleOwner
import androidx.paging.DataSource
import androidx.paging.PagedList

/**
 * @author Davide Giuseppe Farella.
 * An [AbsViewStateStore] that support Android's `Paging`
 */
class PagedViewStateStore<V>( private var pageSize: Int = 20 ) : AbsViewStateStore<PagedList<V>>() {

    /** A [DataSource.Factory] for retrieve [V] */
    private lateinit var factory: DataSource.Factory<Int, V>

    /** @return a new instance of [PagedViewStateObserver] */
    override fun onCreateViewStateObserver( owner: LifecycleOwner? ) =
            PagedViewStateObserver( factory, pageSize, owner )

    /** Set the [factory] */
    @UiThread
    fun setDataSource( factory: DataSource.Factory<Int, V> ) {
        this.factory = factory
    }
}