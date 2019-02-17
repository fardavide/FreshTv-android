@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package studio.forface.freshtv.commonandroid.viewstate

import androidx.lifecycle.MutableLiveData

/**
 * @author Davide Giuseppe Farella.
 * By an idea of Fabio Collini at
 * https://proandroiddev.com/how-to-unit-test-livedata-and-lifecycle-components-8a0af41c90d9
 *
 * Inherit from [AbsViewStateStore]
 */
class ViewStateStore<V>( initialState: ViewState<V> = ViewState.None ): AbsViewStateStore<V>() {

    /** A secondary constructor for implicitly create a [ViewState.Success] passing the [V] data */
    constructor( data: V ): this( ViewState.Success( data ) )

    /** An instance of [MutableLiveData] of [ViewState] of [V] for dispatch [ViewState]'s */
    override val liveData = MutableLiveData<ViewState<V>>().apply {
        value = initialState
    }
}