package studio.forface.freshtv.services

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidComponent
import studio.forface.freshtv.commonandroid.utils.enqueueUniqueWork
import studio.forface.freshtv.commonandroid.utils.getString
import studio.forface.freshtv.commonandroid.utils.workManager
import studio.forface.freshtv.domain.usecases.RefreshTvGuides.Error.Multi
import studio.forface.freshtv.domain.usecases.RefreshTvGuides.Error.Single
import studio.forface.freshtv.domain.usecases.RefreshTvGuides

/**
 * @author Davide Giuseppe Farella.
 * A [Worker] for refresh `TvGuide`s
 */
class RefreshTvGuidesWorker(
    context: Context,
    params: WorkerParameters
): Worker( context, params ), AndroidComponent {

    companion object {
        /** An unique name for the [RefreshTvGuidesWorker] */
        private const val WORKER_NAME = "refresh_tv_guides"
        /** The name of the argument of `EPG`s path in `Work`s Data */
        private const val ARG_EPG_PATH = "epg_path"

        /** Enqueue [RefreshTvGuidesWorker] without params, for refresh from all the `EPG`s */
        fun enqueue() {
            workManager.enqueueUniqueWork<RefreshTvGuidesWorker>(
                    WORKER_NAME, replacePolicy = ExistingWorkPolicy.REPLACE
            )
        }

        /** Enqueue [RefreshTvGuidesWorker] for refresh from a single `EPG`s with the given [epgPath] */
        fun enqueue( epgPath: String ) {
            val work = OneTimeWorkRequestBuilder<RefreshTvGuidesWorker>()
                    .setInputData( workDataOf( ARG_EPG_PATH to epgPath ) )
                    .build()
            workManager.enqueueUniqueWork(
                    "$WORKER_NAME$epgPath",
                    ExistingWorkPolicy.REPLACE,
                    work
            )
        }

        /** Cancel [RefreshTvGuidesWorker] for the given [epgPath] */
        fun cancel( epgPath: String ) {
            workManager.cancelUniqueWork("$WORKER_NAME$epgPath" )
        }
    }

    /** An instance of [RefreshTvGuides] */
    private val refreshTvGuides by inject<RefreshTvGuides>()

    /**
     * @see Worker.doWork
     * @return [success] if [refreshTvGuides] completes without exceptions, else [retry]
     */
    override fun doWork(): Result {
        val playlistPath = inputData.getString( ARG_EPG_PATH )
        val catching = runCatching {
            runBlocking {
                playlistPath?.let { refreshTvGuides( it ) } ?: refreshTvGuides()
            }
        }

        catching
            .onSuccess {
                val errorMessage = when ( it ) {
                    is Single -> getString(
                        R.string.read_single_epg_error_count_args,
                        it.parsingErrors.size,
                        it.epg.name ?: it.epg.path
                    )
                    is Multi -> getString(
                        R.string.read_multi_epg_error_count_args,
                        it.all.flatMap { singleError -> singleError.parsingErrors }.size,
                        it.all.size
                    )
                }
                notifier.error( errorMessage )
                return success()
            }
            .onFailure {
                notifier.error( it )
                return retry()
            }

        throw AssertionError( "Unreachable code" )
    }
}