package studio.forface.freshtv.services

import android.content.Context
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.*
import kotlinx.coroutines.runBlocking
import org.koin.core.inject
import org.threeten.bp.Duration
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.NotifiableWorker
import studio.forface.freshtv.commonandroid.utils.*
import studio.forface.freshtv.domain.usecases.RefreshChannels
import studio.forface.freshtv.domain.usecases.RefreshChannels.Error.Multi
import studio.forface.freshtv.domain.usecases.RefreshChannels.Error.Single
import studio.forface.freshtv.ui.EditPlaylistFragment

/**
 * A [Worker] for refresh `Channel`s
 * Inherit from [NotifiableWorker]
 *
 * @author Davide Giuseppe Farella
 */
class RefreshChannelsWorker(
    context: Context,
    params: WorkerParameters
): NotifiableWorker( context, params ) {

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
                workData = workDataOf(ARG_PLAYLIST_PATH to playlistPath )
            )
        }

        /** Cancel [RefreshChannelsWorker] for the given [playlistPath] */
        fun cancel( playlistPath: String ) {
            workManager.cancelUniqueWork("$WORKER_NAME$playlistPath" )
        }
    }

    /** @see NotifiableWorker.notificationFailureTitle */
    override val notificationFailureTitle: Int = R.string.notification_refresh_channels_failure_title

    /** @see NotifiableWorker.notificationSuccessTitle */
    override val notificationSuccessTitle: Int = R.string.notification_refresh_channels_success_title

    /** An instance of [RefreshChannels] */
    private val refreshChannels by inject<RefreshChannels>()

    /**
     * @see NotifiableWorker.doNotifiableWork
     * @return [onWorkSucceed] if [refreshChannels] completes without exceptions, else [onWorkFailed]
     */
    override fun doNotifiableWork(): NotifiableResult {
        val playlistPathArg = inputData.getString( ARG_PLAYLIST_PATH )
        val catching = runCatching {
            runBlocking {
                playlistPathArg?.let { refreshChannels( it ) } ?: refreshChannels()
            }
        }

        RemoveEmptyGroupsWorker.enqueue()

        catching
            .onSuccess { error ->
                return showResult( error )
            }
            .onFailure {
                val playlistPath = ( it as RefreshChannels.FatalException ).playlist.path
                val resolution = NavDeepLinkBuilder( applicationContext )
                    .setGraph( R.navigation.nav_graph )
                    .setDestination( R.id.editPlaylistFragment )
                    .setArguments( bundleOf(EditPlaylistFragment.ARG_PLAYLIST_PATH to playlistPath ) )
                    .createPendingIntent()
                return onWorkFailed( playlistPath, it, resolution)
            }

        throw AssertionError("Unreachable code" )
    }

    /**
     * Show the result to the user
     * @return [onWorkSucceed]
     */
    private fun showResult( error: RefreshChannels.Error ): NotifiableResult {
        return if ( error.hasError ) {
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
            onWorkSucceed( WORKER_NAME, errorMessage, SuccessLevel.PARTIAL)

        } else onWorkSucceed( WORKER_NAME, getString( R.string.refresh_channels_completed ), SuccessLevel.FULL)
    }
}