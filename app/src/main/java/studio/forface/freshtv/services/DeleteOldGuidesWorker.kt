package studio.forface.freshtv.services

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import org.koin.core.inject
import org.threeten.bp.Duration
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidComponent
import studio.forface.freshtv.commonandroid.utils.enqueueUniquePeriodicWork
import studio.forface.freshtv.commonandroid.utils.workManager
import studio.forface.freshtv.domain.usecases.DeleteOldGuides
import java.util.concurrent.TimeUnit

/**
 * @author Davide Giuseppe Farella.
 * A [Worker] for delete old Tv guides
 */
class DeleteOldGuidesWorker(
    context: Context,
    params: WorkerParameters
): Worker( context, params ), AndroidComponent {

    companion object {
        /** An unique name for the [DeleteOldGuidesWorker] */
        private const val WORKER_NAME = "delete_old_tv_guides"

        /** Enqueue [DeleteOldGuidesWorker] as [PeriodicWorkRequest] for upload a large batch */
        fun enqueue( repeatInterval: Duration, flexInterval: Duration? = null ) {
            workManager.enqueueUniquePeriodicWork<DeleteOldGuidesWorker>( WORKER_NAME, repeatInterval, flexInterval )
        }
    }

    /** An instance of [DeleteOldGuides] */
    private val deleteOldGuides by inject<DeleteOldGuides>()

    /**
     * @see Worker.doWork
     * @return [success] if [deleteOldGuides] completes without exceptions, else [retry]
     */
    override fun doWork(): Result {
        runCatching { deleteOldGuides() }
            .onFailure { notifier.error( it ); return retry() }
        return success()
    }
}