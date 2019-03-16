package studio.forface.freshtv.services

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidComponent
import studio.forface.freshtv.commonandroid.utils.enqueueUniqueWork
import studio.forface.freshtv.commonandroid.utils.workManager
import studio.forface.freshtv.domain.usecases.RemovePlaylist

/**
 * @author Davide Giuseppe Farella.
 * A [Worker] for remove a `Playlist` and its `Channel`s
 */
class RemovePlaylistWorker(
    context: Context,
    params: WorkerParameters
) : Worker( context, params ), AndroidComponent {

    companion object {
        /** An unique name for the [RemovePlaylistWorker] */
        private const val WORKER_NAME = "remove_playlist"
        /** The name of the argument of `Playlist`s path in `Work`s Data */
        private const val ARG_PLAYLIST_PATH = "playlist_path"

        /**
         * Enqueue [RemovePlaylistWorker] for remove from a single `Playlist`s with the given
         * [playlistPath]
         */
        fun enqueue( playlistPath: String ) {
            workManager.enqueueUniqueWork<RemovePlaylistWorker>(
                uniqueWorkName = "$WORKER_NAME$playlistPath",
                replacePolicy = ExistingWorkPolicy.REPLACE,
                workData = workDataOf( ARG_PLAYLIST_PATH to playlistPath )
            )
        }
    }

    /** An instance of [RemovePlaylist] */
    private val removePlaylist by inject<RemovePlaylist>()

    /**
     * @see Worker.doWork
     * @return [success] if [removePlaylist] completes without exceptions, else [retry]
     */
    override fun doWork(): Result {
        val playlistPath = inputData.getString( ARG_PLAYLIST_PATH )!!
        val catching = runCatching {
            runBlocking { removePlaylist( playlistPath ) }
        }

        catching
            .onSuccess {
                RefreshChannelsWorker.cancel( playlistPath )
                return success()
            }
            .onFailure {
                notifier.error( it )
                return retry()
            }

        throw AssertionError( "Unreachable code" )
    }
}