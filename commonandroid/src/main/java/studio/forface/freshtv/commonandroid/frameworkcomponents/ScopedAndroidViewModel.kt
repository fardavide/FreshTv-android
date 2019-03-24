package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author Davide Giuseppe Farella.
 * An abstract class for [AndroidViewModel]'s that implements [CoroutineScope].
 *
 * Inherit from [AndroidViewModel].
 * Implements [CoroutineScope].
 */
abstract class ScopedAndroidViewModel( application: Application ): AndroidViewModel( application ), CoroutineScope {

    /** @return [Context] from [getApplication] */
    val context: Context get() = getApplication()

    /** An instance of [Job] for [CoroutineContext] */
    private val job = Job()

    /** An instance of [CoroutineContext] for [CoroutineScope] */
    override val coroutineContext = job + IO

    /** When the [ViewModel] is cleared, call [Job.cancel] on [job] and stop any suspended function */
    override fun onCleared() {
        job.cancel()
    }
}