@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package studio.forface.freshtv.commonandroid.viewstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Davide Giuseppe Farella.
 * TODO
 */
abstract class AbsViewStateStore<V> {

    /**
     * This property will store the last available [ViewState.data], so it will be emitted every
     * time. It is useful if, for instance, we have a failure after rotating the screen: in
     * that case the data would not be emitter.
     */
    private var data: V? = null

    /**
     * This property will store the last [ViewState.Error], so it won't be delivered every
     * time for every new [Observer] so, if for instance we rotate the screen, the last
     * error won't be delivered again.
     */
    private var lastError: ViewState.Error? = null

    /** An instance of [MutableLiveData] of [ViewState] of [V] for dispatch [ViewState]'s */
    @PublishedApi
    internal open val liveData = MutableLiveData<ViewState<V>>()

    /**
     * @return an instance of [Observer] that will save [ViewState.data] in case of success and then
     * trigger the callbacks the following callbacks on the given [ViewStateObserver]:
     * [ViewStateObserver.onEach], [ViewStateObserver.onData], [ViewStateObserver.onError] and
     * [ViewStateObserver.onLoadingChange].
     */
    @PublishedApi
    internal fun observerWith( observer: ViewStateObserver<V> ) = observer.run {
        Observer<ViewState<V>> { viewState ->
            // Deliver every ViewState.
            onEach( viewState )

            // Every time the observer is triggered for any reason ( loading change, data or error ),
            // if ViewState is Success, we store the new data then, in every case, if data is not
            // null, we deliver the data.
            viewState.doOnData { data = it }
            data?.let( onData )

            // Every time the observer is triggered for any reason ( loading change, data or error ),
            // we instantiate a new NULL ViewState.Error on newError then, if ViewState is Error
            // we store the value in newError then, if error is different from lastError, we
            // deliver it if not Null and store in lastError.
            var newError: ViewState.Error? = null
            viewState.doOnError { error -> newError = error }
            if ( newError !== lastError ) {
                newError?.let( onError )
                lastError = newError
            }

            // If View.Stare is a loading change, we deliver it.
            viewState.doOnLoadingChange( onLoadingChange )
        }
    }

    /**
     * @see LiveData.observe with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observe( owner: LifecycleOwner, block: ViewStateObserver<V>.() -> Unit ) {
        val observer = onCreateViewStateObserver( owner )
        observer.block()
        liveData.observe( owner, observerWith( observer ) )
    }

    /**
     * Observe the [ViewStateStore] and trigger [block] only on [ViewStateObserver.onData]
     * @see LiveData.observe with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observeData( owner: LifecycleOwner, crossinline block: V.() -> Unit ) {
        val observer = onCreateViewStateObserver( owner )
        observer.onData = { it.block() }
        liveData.observe( owner, observerWith( observer ) )
    }

    /**
     * @see LiveData.observeForever with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observeForever( block: ViewStateObserver<V>.() -> Unit ) {
        val observer = onCreateViewStateObserver()
        observer.block()
        liveData.observeForever( observerWith( observer ) )
    }

    /**
     * Observe the [ViewStateStore] and trigger [block] only on [ViewStateObserver.onData]
     * @see LiveData.observeForever with an [Observer] created with an instance of [ViewStateObserver]
     * @param block a lambda with [ViewStateObserver] as receiver that properly sets callbacks on it.
     */
    inline fun observeDataForever( crossinline block: V.() -> Unit ) {
        val observer = onCreateViewStateObserver()
        observer.onData = { it.block() }
        liveData.observeForever( observerWith( observer ) )
    }

    /** @return a new instance of [ViewStateObserver] */
    @PublishedApi
    internal open fun onCreateViewStateObserver( owner: LifecycleOwner? = null ) =
            ViewStateObserver<V>()

    /** @see MutableLiveData.postValue on [liveData] */
    fun postState( state: ViewState<V> ) {
        liveData.postValue( state )
    }

    /** @see MutableLiveData.setValue of [liveData] */
    fun setState( state: ViewState<V> ) {
        liveData.value = state
    }

    /** @see LiveData.getValue on [liveData] */
    fun state() = liveData.value!!
}

/**
 * Post a [ViewState.Success] with the given [data].
 * @see ViewStateStore.postState
 */
fun <V> AbsViewStateStore<V>.postData( data: V ) {
    postState( ViewState.Success( data ) )
}

/**
 * Post a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.postError( errorThrowable: Throwable ) {
    postState( ViewState.Error.fromThrowable( errorThrowable ) )
}

/**
 * Post a [ViewState.Loading].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.postLoading() {
    postState( ViewState.Loading )
}

/**
 * Set a [ViewState.Success] with the given [data].
 * @see ViewStateStore.setState
 */
fun <V> AbsViewStateStore<V>.setData( data: V ) {
    setState( ViewState.Success( data ) )
}

/**
 * Set a [ViewState.Error] created from the given [errorThrowable].
 * @see ViewStateStore.setState
 */
fun AbsViewStateStore<*>.setError( errorThrowable: Throwable ) {
    setState( ViewState.Error.fromThrowable( errorThrowable ) )
}

/**
 * Set a [ViewState.Loading].
 * @see ViewStateStore.postState
 */
fun AbsViewStateStore<*>.setLoading() {
    setState( ViewState.Loading )
}