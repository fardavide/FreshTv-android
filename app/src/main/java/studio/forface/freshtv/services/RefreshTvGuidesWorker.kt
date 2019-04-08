package studio.forface.freshtv.services

import android.content.Context
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.*
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import org.threeten.bp.Duration
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.NotifiableWorker
import studio.forface.freshtv.commonandroid.utils.*
import studio.forface.freshtv.domain.usecases.RefreshTvGuides
import studio.forface.freshtv.domain.usecases.RefreshTvGuides.Error.Multi
import studio.forface.freshtv.domain.usecases.RefreshTvGuides.Error.Single
import studio.forface.freshtv.ui.EditEpgFragment

/**
 * A [Worker] for refresh `TvGuide`s
 * Inherit from [NotifiableWorker]
 *
 * @author Davide Giuseppe Farella
 */
class RefreshTvGuidesWorker(
    context: Context,
    params: WorkerParameters
): NotifiableWorker( context, params ) {

    companion object {
        /** An unique name for the [RefreshTvGuidesWorker] */
        const val WORKER_NAME = "refresh_tv_guides"
        /** The name of the argument of `EPG`s path in `Work`s Data */
        private const val ARG_EPG_PATH = "epg_path"

        /** Enqueue [RefreshTvGuidesWorker] without params, for refresh from all the `EPG`s */
        fun enqueue( repeatInterval: Duration, flexInterval: Duration? = null ) {
            val constraints = WorkConstraints {
                if ( Android.MARSHMALLOW ) setRequiresDeviceIdle( true )
                setRequiredNetworkType( NetworkType.UNMETERED )
                setRequiresCharging( true )
            }
            workManager.enqueueUniquePeriodicWork<RefreshTvGuidesWorker>(
                    WORKER_NAME, repeatInterval, flexInterval, constraints = constraints
            )
        }

        /** Enqueue [RefreshTvGuidesWorker] for refresh from a single `EPG`s with the given [epgPath] */
        fun enqueue( epgPath: String ) {
            //val constraints = WorkConstraints {
            //    if ( Android.MARSHMALLOW ) setRequiresDeviceIdle( true )
            //}
            workManager.enqueueUniqueWork<RefreshTvGuidesWorker>(
                uniqueWorkName = "$WORKER_NAME$epgPath",
                replacePolicy = ExistingWorkPolicy.REPLACE,
                workData = workDataOf( ARG_EPG_PATH to epgPath )//,
            //    constraints = constraints
            )
        }

        /** Cancel [RefreshTvGuidesWorker] for the given [epgPath] */
        fun cancel( epgPath: String ) {
            workManager.cancelUniqueWork("$WORKER_NAME$epgPath" )
        }
    }

    /** @see NotifiableWorker.notificationFailureTitle */
    override val notificationFailureTitle: Int = R.string.notification_refresh_tv_guide_failure_title

    /** @see NotifiableWorker.notificationSuccessTitle */
    override val notificationSuccessTitle: Int = R.string.notification_refresh_tv_guide_success_title

    /** An instance of [RefreshTvGuides] */
    private val refreshTvGuides by inject<RefreshTvGuides>()

    /**
     * @see Worker.doWork
     * @return [success] if [refreshTvGuides] completes without exceptions, else [retry]
     */
    override fun doNotifiableWork(): NotifiableResult {

        //refreshTvGuides.onProgress { Timber.i( "Progress: $it" ) } // TODO

        val epgPathArg = inputData.getString( ARG_EPG_PATH )
        val catching = runCatching {
            runBlocking {
                epgPathArg?.let { refreshTvGuides( it ) } ?: refreshTvGuides()
            }
        }

        catching
            .onSuccess { error ->
                return showResult( error )
            }
            .onFailure {
                val epgPath = ( it as RefreshTvGuides.FatalException ).epg.path
                val resolution = NavDeepLinkBuilder( applicationContext )
                    .setGraph( R.navigation.nav_graph )
                    .setDestination( R.id.editEpgFragment )
                    .setArguments( bundleOf(EditEpgFragment.ARG_EPG_PATH to epgPath ) )
                    .createPendingIntent()
                return onWorkFailed( epgPath, it, resolution)
            }

        throw AssertionError("Unreachable code" )
    }

    /** Show the result to the user */
    private fun showResult( error: RefreshTvGuides.Error ): NotifiableResult {
        return if ( error.hasError ) {
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
            onWorkSucceed( WORKER_NAME, errorMessage, SuccessLevel.PARTIAL)

        } else onWorkSucceed( WORKER_NAME, getString( R.string.refresh_tv_guides_completed ), SuccessLevel.FULL)
    }
}