package studio.forface.freshtv.commonandroid.frameworkcomponents

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import org.threeten.bp.Duration
import studio.forface.freshtv.domain.utils.seconds
import studio.forface.viewstatestore.paging.ViewStateStoreScope
import kotlin.coroutines.CoroutineContext

/**
 * @author Davide Giuseppe Farella.
 * An abstract class for [ViewModel]'s that implements [CoroutineScope].
 *
 * Inherit from [ViewModel].
 * Implements [CoroutineScope].
 * Implements [ViewStateStoreScope].
 */
abstract class ScopedViewModel(
    defaultDispatcher: CoroutineDispatcher = Default
): ViewModel(), CoroutineScope, ViewStateStoreScope {

    companion object {
        /** A [Duration] representing the delay to another try after an error occurred */
        val DEFAULT_ERROR_DELAY = 5.seconds
    }

    /** An instance of [Job] for [CoroutineContext] */
    private val job = Job()

    /** An instance of [CoroutineContext] for [CoroutineScope] */
    override val coroutineContext = job + defaultDispatcher

    /** When the [ViewModel] is cleared, call [Job.cancel] on [job] and stop any suspended function */
    override fun onCleared() {
        job.cancel()
    }
}