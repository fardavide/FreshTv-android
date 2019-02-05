package studio.forface.freshtv.commonandroid.viewstate

import androidx.annotation.StringRes
import studio.forface.freshtv.commonandroid.viewstate.ViewState.Error.Companion.fromThrowable
import studio.forface.freshtv.domain.utils.nonNullMessage

/**
 * @author Davide Giuseppe Farella.
 * This class is basically a Model that the UI will receive from ViewModel.
 */
sealed class ViewState<out T> {

    /**
     * An instance of [T] that will be available in case of [Success], else it will be null.
     */
    open val data: T? = null

    /**
     * A function for map the current [data].
     */
    abstract fun <R> map( mapper: (T) -> R ): ViewState<R>

    /**
     * Execute an [action] in case of [Success].
     */
    inline fun doOnData( action: (T) -> Unit ) {
        if ( this is Success) action( data )
    }

    /**
     * Execute an [action] in case of [Error].
     */
    inline fun doOnError( action: (Error) -> Unit ) {
        if ( this is Error) action( this )
    }

    /**
     * Execute an [action] whether is [Loading] or not.
     */
    inline fun doOnLoadingChange( action: (isLoading: Boolean) -> Unit ) {
        action( this is Loading)
    }

    /**
     * A class that represents the success and will contains the [data] [T].
     */
    data class Success<out T>( override val data: T ) : ViewState<T>() {
        override fun <R> map( mapper: (T) -> R ): ViewState<R> =
            Success( mapper( data ) )
    }

    /**
     * A class that represents a failure and will contains the relative [throwable].
     * @constructor is protected and cannot be instantiated outside of this file, use [fromThrowable] instead.
     */
    open class Error protected constructor ( val throwable: Throwable ) : ViewState<Nothing>() {

        companion object {
            /**
             * Create an instance of [ViewState.Error] or a custom extension for the the given
             * [throwable].
             * @return [ViewState.Error].
             */
            @Suppress("WhenWithOnlyElse")
            fun fromThrowable( throwable: Throwable ) = when ( throwable ) {
                else -> ViewState.Error( throwable )
            }
        }

        /**
         * GET a [Throwable.nonNullMessage] from [throwable].
         */
        val baseMessage: String get() = throwable.nonNullMessage

        /**
         * The [StringRes] for a custom message to show to the user.
         */
        @get:StringRes
        open val customMessageRes: Int? = null

        /**
         * [Throwable.printStackTrace] from [throwable].
         */
        fun printStackTrace() = throwable.printStackTrace()

        override fun <R> map( mapper: (Nothing) -> R ): ViewState<R> = this
    }

    /**
     * A class that represents the loading state and will contains [Nothing].
     */
    object Loading : ViewState<Nothing>() {
        override fun <R> map( mapper: (Nothing) -> R ): ViewState<R> = this
    }

    /**
     * A class that represents the unknown state and will contains [Nothing].
     */
    object None : ViewState<Nothing>() {
        override fun <R> map( mapper: (Nothing) -> R ): ViewState<R> = this
    }
}