package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.koin.core.inject
import studio.forface.freshtv.commonandroid.R
import studio.forface.freshtv.commonandroid.utils.Android
import studio.forface.viewstatestore.ViewState


/**
 * An abstract [Worker] that will show a notification in case of Success and Failure.
 *
 * @author Davide Giuseppe Farella
 */
abstract class NotifiableWorker(
    context: Context,
    params: WorkerParameters
) : Worker( context, params ), AndroidComponent {

    /** A [StringRes] for notification's channel id in case of FAILURE */
    @StringRes private val notificationFailureChannelId: Int = R.string.notification_error_channel_id

    /** A [StringRes] for notification's channel name in case of FAILURE */
    @StringRes private val notificationFailureChannelName: Int = R.string.notification_error_channel_name

    /** A [StringRes] for notification's title in case of FAILURE */
    @get:StringRes protected abstract val notificationFailureTitle: Int


    /** A [StringRes] for notification's channel id in case of SUCCESS */
    @StringRes private val notificationSuccessChannelId: Int = R.string.notification_success_channel_id

    /** A [StringRes] for notification's channel name in case of SUCCESS */
    @StringRes private val notificationSuccessChannelName: Int = R.string.notification_success_channel_name

    /** A [StringRes] for notification's title in case of SUCCESS */
    @get:StringRes protected abstract val notificationSuccessTitle: Int


    /** An instance of [NotificationManager] for create notifications */
    private val notificationManager by inject<NotificationManager>()

    /** Override [doWork] for call [doNotifiableWork] */
    override fun doWork() = doNotifiableWork().baseResult

    /**
     * @see Worker.doWork
     * @return [NotifiableResult]
     */
    abstract fun doNotifiableWork() : NotifiableResult

    /**
     * When work fails
     * Creates a [ViewState.Error] from the given [Throwable] and call [onWorkFailed]
     *
     * @return [NotifiableResult.FAILURE]
     */
    protected fun onWorkFailed( entityId: String, throwable: Throwable, resolution: PendingIntent ) =
        onWorkFailed( entityId,ViewState.Error.fromThrowable( throwable ), resolution )

    /**
     * Create a Notification for notify the failure
     * @return [NotifiableResult.FAILURE]
     */
    private fun onWorkFailed( entityId: String, error: ViewState.Error, resolution: PendingIntent ): NotifiableResult {
        notifier.error( error )

        val notification = NotificationCompat.Builder( applicationContext, notificationFailureChannelId.string() )
            .setSmallIcon( R.drawable.ic_app_small_icon )
            .setContentTitle( notificationFailureTitle.string() )
            .setContentText( error.getMessage( applicationContext ) )
            .addAction( R.drawable.ic_wrench_black, R.string.notification_error_action_resolve.string(), resolution )
            .setAutoCancel( true )
            .setSound( Settings.System.DEFAULT_NOTIFICATION_URI )
            .build()

        if ( Android.OREO ) createFailureNotificationChannel()
        notificationManager.notify( entityId.hashCode(), notification )

        return NotifiableResult.FAILURE
    }

    /** Create [NotificationChannel] for failures for [Android.OREO] */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createFailureNotificationChannel() {
        val notificationChannel = NotificationChannel(
            notificationFailureChannelId.string(),
            notificationFailureChannelName.string(),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = R.string.notification_error_channel_description.string()
            enableLights(true )
            lightColor = Color.RED
            enableVibration(true )
            vibrationPattern = longArrayOf( 100, 200, 300, 400, 500, 400, 300, 200, 400 )
        }

        notificationManager.createNotificationChannel( notificationChannel )
    }

    /**
     * Create a Notification for notify the success
     * @return [NotifiableResult.FAILURE]
     */
    fun onWorkSucceed(
        entityId: String,
        message: CharSequence,
        level: SuccessLevel = SuccessLevel.FULL
    ): NotifiableResult {
        when( level ) {
            SuccessLevel.FULL -> notifier.info( message )
            SuccessLevel.PARTIAL -> notifier.warn( message )
        }
        notifier.info( message )

        val notification = NotificationCompat.Builder( applicationContext, notificationSuccessChannelId.string() )
            .setSmallIcon( R.drawable.ic_app_small_icon )
            .setContentTitle( notificationSuccessTitle.string() )
            .setContentText( message )
            .setAutoCancel( true )
            .build()

        if ( Android.OREO ) createSuccessNotificationChannel()
        notificationManager.notify( entityId.hashCode(), notification )

        return NotifiableResult.SUCCESS
    }

    /** Create [NotificationChannel] for success for [Android.OREO] */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createSuccessNotificationChannel() {
        val notificationChannel = NotificationChannel(
            notificationSuccessChannelId.string(),
            notificationSuccessChannelName.string(),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = R.string.notification_success_channel_description.string()
        }

        notificationManager.createNotificationChannel( notificationChannel )
    }

    /** A shortcut for call [Context.getString] from an [Int] */
    private fun Int.string() = applicationContext.getString(this )

    /** An enum for the success level */
    enum class SuccessLevel { FULL, PARTIAL }

    /** A result for [NotifiableWorker] that wraps a [Result] */
    sealed class NotifiableResult( val baseResult: Result ) {
        internal object SUCCESS : NotifiableResult( success() )
        internal object FAILURE : NotifiableResult( retry() )
    }
}