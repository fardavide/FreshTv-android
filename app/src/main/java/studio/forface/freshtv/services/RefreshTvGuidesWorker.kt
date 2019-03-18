package studio.forface.freshtv.services

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import org.threeten.bp.Duration
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidComponent
import studio.forface.freshtv.commonandroid.utils.*
import studio.forface.freshtv.domain.usecases.RefreshTvGuides
import studio.forface.freshtv.domain.usecases.RefreshTvGuides.Error.Multi
import studio.forface.freshtv.domain.usecases.RefreshTvGuides.Error.Single
import timber.log.Timber

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
        fun enqueue( repeatInterval: Duration, flexInterval: Duration? = null ) {
            val constraints = WorkConstraints {
                setRequiredNetworkType( NetworkType.UNMETERED )
                setRequiresCharging( true )
            }
            workManager.enqueueUniquePeriodicWork<RefreshTvGuidesWorker>(
                    WORKER_NAME, repeatInterval, flexInterval, constraints = constraints
            )
        }

        /** Enqueue [RefreshTvGuidesWorker] for refresh from a single `EPG`s with the given [epgPath] */
        fun enqueue( epgPath: String ) {
            workManager.enqueueUniqueWork<RefreshTvGuidesWorker>(
                uniqueWorkName = "$WORKER_NAME$epgPath",
                replacePolicy = ExistingWorkPolicy.REPLACE,
                workData = workDataOf( ARG_EPG_PATH to epgPath )
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

        //refreshTvGuides.onProgress { Timber.i( "Progress: $it" ) } // TODO

        val playlistPath = inputData.getString( ARG_EPG_PATH )
        val catching = runCatching {
            runBlocking {
                playlistPath?.let { refreshTvGuides( it ) } ?: refreshTvGuides()
            }
        }

        catching
            .onSuccess { error ->
                showResult( error )
                return success()
            }
            .onFailure {
                notifier.error( it )
                return retry()
            }

        throw AssertionError( "Unreachable code" )
    }

    /** Show the result to the user */
    private fun showResult( error: RefreshTvGuides.Error ) {
        if ( error.hasError ) {
            val errorMessage = when ( error ) {
                is Single -> getString(
                    R.string.read_single_epg_error_count_args,
                    error.parsingErrors.size,
                    error.epg.name ?: error.epg.path
                )
                is Multi -> getString(
                    R.string.read_multi_epg_error_count_args,
                    error.all.flatMap { singleError -> singleError.parsingErrors }.size,
                    error.all.size
                )
            }
            notifier.warn( errorMessage )

        } else notifier.info( getString( R.string.refresh_tv_guides_completed ) )
    }
}