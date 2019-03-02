package studio.forface.freshtv.commonandroid.frameworkcomponents

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author Davide Giuseppe Farella.
 * An abstract class for [ViewModel]'s that implements [CoroutineScope].
 *
 * Inherit from [ViewModel].
 * Implements [CoroutineScope].
 */
abstract class ScopedViewModel: ViewModel(), CoroutineScope {

    companion object {
        const val DEFAULT_ERROR_DELAY = 30_000L
    }

    /** An instance of [Job] for [CoroutineContext] */
    private val job = Job()

    /** An instance of [CoroutineContext] for [CoroutineScope] */
    override val coroutineContext = job + Main

    /** When the [ViewModel] is cleared, call [Job.cancel] on [job] and stop any suspended function */
    override fun onCleared() {
        job.cancel()
    }
}