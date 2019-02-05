package studio.forface.freshtv.commonandroid.frameworkcomponents

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    /** An instance of [Job] for [CoroutineContext] */
    private val job = Job()

    /** An instance of [CoroutineContext] for [CoroutineScope] */
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    /** When the [ViewModel] is cleared, call [Job.cancel] on [job] and stop any suspended function */
    override fun onCleared() {
        job.cancel()
    }
}