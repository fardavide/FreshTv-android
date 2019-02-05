package studio.forface.freshtv.commonandroid.viewstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Davide Giuseppe Farella.
 * By an idea of Fabio Collini at
 * https://proandroiddev.com/how-to-unit-test-livedata-and-lifecycle-components-8a0af41c90d9
 */
class ViewStateStore<V>( initialState: ViewState<V> = ViewState.None) {

    /** A secondary constructor for implicitly create a [ViewState.Success] passing the [V] data */
    constructor( data: V ): this( ViewState.Success( data ) )

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

    /**
     * An instance of [MutableLiveData] of [ViewState] of [V] for dispatch [ViewState]'s.
     */
    private val liveData = MutableLiveData<ViewState<V>>().apply {
        value = initialState
    }

    /**
     * A callback that will be triggered on [ViewState.doOnData].
     */
    private var onData: ( (V) -> Unit ) = {}

    /**
     * A callback that will be triggered on each [ViewState].
     */
    private var onEach: ( (ViewState<V>) -> Unit ) = {}

    /**
     * A callback that will be triggered on [ViewState.doOnError].
     */
    private var onError: ( (ViewState.Error) -> Unit ) = {}

    /**
     * A callback that will be triggered on [ViewState.doOnLoadingChange].
     */
    private var onLoadingChange: ( (Boolean) -> Unit ) = {}

    /**
     * An instance of [Observer] that will save [ViewState.data] in case of success and then
     * trigger the callbacks [onEach], [onData], [onError] and [onLoadingChange].
     */
    private val observer: Observer<ViewState<V>> = Observer {
        // Deliver every ViewState.
        onEach( it )

        // Every time the observer is triggered for any reason ( loading change, data or error ),
        // if ViewState is Success, we store the new data then, in every case, if data is not
        // null, we deliver the data.
        it.doOnData { data -> this.data = data }
        data?.let( onData )

        // Every time the observer is triggered for any reason ( loading change, data or error ),
        // we instantiate a new NULL ViewState.Error on newError then, if ViewState is Error
        // we store the value in newError then, if error is different from lastError, we
        // deliver it if not Null and store in lastError.
        var newError: ViewState.Error? = null
        it.doOnError { error -> newError = error }
        if ( newError !== lastError ) {
            newError?.let( onError )
            lastError = newError
        }

        // If View.Stare is a loading change, we deliver it.
        it.doOnLoadingChange( onLoadingChange )
    }

    /**
     * Run [action] for every event.
     */
    fun doOnEach( action: (ViewState<V>) -> Unit ) = apply { onEach = action }

    /**
     * Run [action] when data is received correctly.
     */
    fun doOnData( action: (V) -> Unit ) = apply { onData = action }

    /**
     * Run [action] when an error occurs.
     */
    fun doOnError( action: (ViewState.Error) -> Unit ) = apply { onError = action }

    /**
     * Run [action] when the loading state changes.
     */
    fun doOnLoadingChange( action: (Boolean) -> Unit ) = apply { onLoadingChange = action }

    /**
     * @see LiveData.observe with our [observer].
     */
    fun observe( owner: LifecycleOwner) = liveData.observe( owner, observer )

    /**
     * @see LiveData.observeForever with our [observer].
     */
    fun observeForever() = liveData.observeForever( observer )

    /**
     * Post a [ViewState.Success] with the given [data].
     * @see postState
     */
    fun postData( data: V) {
        postState(ViewState.Success(data))
    }

    /**
     * @see MutableLiveData.postValue on [liveData].
     */
    fun postState( state: ViewState<V>) {
        liveData.postValue( state )
    }

    /**
     * Set a [ViewState.Success] with the given [data].
     * @see setState
     */
    fun setData( data: V) {
        setState(ViewState.Success(data))
    }

    /**
     * @see MutableLiveData.setValue of [liveData].
     */
    fun setState( state: ViewState<V>) {
        liveData.value = state
    }

    /**
     * @see LiveData.getValue on [liveData].
     */
    fun state() = liveData.value!!
}