package studio.forface.freshtv.services

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidComponent
import studio.forface.freshtv.commonandroid.utils.enqueueUniqueWork
import studio.forface.freshtv.commonandroid.utils.workManager
import studio.forface.freshtv.domain.usecases.DeleteOldGuides
import studio.forface.freshtv.domain.usecases.RefreshChannels

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
        /** The name of the argument of `Playlist` path in `Work`s Data */
        private const val ARG_PLAYLIST_PATH = "playlist_path"

        /** Enqueue [RefreshChannelsWorker] without params, for refresh from all the `Playlist`s */
        fun enqueue() {
            workManager.enqueueUniqueWork<RefreshChannelsWorker>(
                    WORKER_NAME, replacePolicy = ExistingWorkPolicy.REPLACE
            )
        }

        /**
         * Enqueue [RefreshChannelsWorker] for refresh from a single `Playlist`s with the given
         * [playlistPath]
         */
        fun enqueue( playlistPath: String ) {
            val work = OneTimeWorkRequestBuilder<RefreshChannelsWorker>()
                    .setInputData( workDataOf(ARG_PLAYLIST_PATH to playlistPath ) )
                    .build()
            workManager.enqueueUniqueWork(
                    "$WORKER_NAME$playlistPath",
                    ExistingWorkPolicy.REPLACE,
                    work
            )
        }
    }

    /** An instance of [DeleteOldGuides] */
    private val refreshChannels by inject<RefreshChannels>()

    /**
     * @see Worker.doWork
     * @return [success] if [refreshChannels] completes without exceptions, else [retry]
     */
    override fun doWork(): Result {
        val playlistPath = inputData.getString( ARG_PLAYLIST_PATH )
        runCatching {
            runBlocking {
                playlistPath?.let { refreshChannels( it ) } ?: refreshChannels()
            }
        }.onFailure { notifier.error( it ); return retry() }

        return success()
    }
}