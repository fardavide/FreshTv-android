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
import studio.forface.freshtv.domain.usecases.RefreshChannels
import studio.forface.freshtv.domain.usecases.RefreshChannels.Error.Multi
import studio.forface.freshtv.domain.usecases.RefreshChannels.Error.Single

/**
 * @author Davide Giuseppe Farella.
 * A [Worker] for refresh `Channel`s
 */
class RefreshChannelsWorker(
    context: Context,
    params: WorkerParameters
): Worker( context, params ), AndroidComponent {

    companion object {
        /** An unique name for the [RefreshChannelsWorker] */
        private const val WORKER_NAME = "refresh_channels"
        /** The name of the argument of `Playlist`s path in `Work`s Data */
        private const val ARG_PLAYLIST_PATH = "playlist_path"

        /** Enqueue [RefreshChannelsWorker] without params, for refresh from all the `Playlist`s */
        fun enqueue( repeatInterval: Duration, flexInterval: Duration? = null ) {
            val constraints = WorkConstraints {
                setRequiredNetworkType( NetworkType.CONNECTED )
            }
            workManager.enqueueUniquePeriodicWork<RefreshChannelsWorker>(
                    WORKER_NAME, repeatInterval, flexInterval, constraints = constraints
            )
        }

        /**
         * Enqueue [RefreshChannelsWorker] for refresh from a single `Playlist`s with the given
         * [playlistPath]
         */
        fun enqueue( playlistPath: String ) {
            workManager.enqueueUniqueWork<RefreshChannelsWorker>(
                uniqueWorkName = "$WORKER_NAME$playlistPath",
                replacePolicy = ExistingWorkPolicy.REPLACE,
                workData = workDataOf( ARG_PLAYLIST_PATH to playlistPath )
            )
        }

        /** Cancel [RefreshChannelsWorker] for the given [playlistPath] */
        fun cancel( playlistPath: String ) {
            workManager.cancelUniqueWork( "$WORKER_NAME$playlistPath" )
        }
    }

    /** An instance of [RefreshChannels] */
    private val refreshChannels by inject<RefreshChannels>()

    /**
     * @see Worker.doWork
     * @return [success] if [refreshChannels] completes without exceptions, else [retry]
     */
    override fun doWork(): Result {
        val playlistPath = inputData.getString( ARG_PLAYLIST_PATH )
        val catching = runCatching {
            runBlocking {
                playlistPath?.let { refreshChannels( it ) } ?: refreshChannels()
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

        throw AssertionError("Unreachable code" )
    }

    /** Show the result to the user */
    private fun showResult( error: RefreshChannels.Error ) {
        if ( error.hasError ) {
            val errorMessage = when ( error ) {
                is Single -> getString(
                    R.string.read_single_playlist_error_count_args,
                    error.parsingErrors.size,
                    error.playlist.name ?: error.playlist.path
                )
                is Multi -> getString(
                    R.string.read_multi_playlist_error_count_args,
                    error.all.flatMap { singleError -> singleError.parsingErrors }.size,
                    error.all.size
                )
            }
            notifier.warn( errorMessage )

        } else notifier.info( getString( R.string.refresh_channels_completed ) )
    }
}