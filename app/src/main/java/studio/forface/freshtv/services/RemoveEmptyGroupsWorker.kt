package studio.forface.freshtv.services

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidComponent
import studio.forface.freshtv.commonandroid.utils.enqueueUniqueWork
import studio.forface.freshtv.commonandroid.utils.workManager
import studio.forface.freshtv.domain.usecases.RemoveEmptyGroups
import studio.forface.freshtv.domain.usecases.RemovePlaylist

/**
 * @author Davide Giuseppe Farella.
 * A [Worker] for remove empty `Group`s
 */
class RemoveEmptyGroupsWorker(
    context: Context,
    params: WorkerParameters
) : Worker( context, params ), AndroidComponent {

    companion object {
        /** An unique name for the [RemoveEmptyGroupsWorker] */
        private const val WORKER_NAME = "remove_empty_groups"

        /** Enqueue [RemoveEmptyGroupsWorker] for remove empty `Group`s */
        fun enqueue() {
            workManager.enqueueUniqueWork<RemoveEmptyGroupsWorker>(
                uniqueWorkName = WORKER_NAME,
                replacePolicy = ExistingWorkPolicy.REPLACE
            )
        }
    }

    /** An instance of [RemovePlaylist] */
    private val removeEmptyGroups by inject<RemoveEmptyGroups>()

    /**
     * @see Worker.doWork
     * @return [success] if [removeEmptyGroups] completes without exceptions, else [retry]
     */
    override fun doWork(): Result {
        val catching = runCatching {
            runBlocking { removeEmptyGroups() }
        }

        catching
            .onSuccess {
                return success()
            }
            .onFailure {
                notifier.error( it )
                return retry()
            }

        throw AssertionError( "Unreachable code" )
    }
}