package studio.forface.freshtv.commonandroid.log

import android.util.Log
import com.crashlytics.android.Crashlytics
import studio.forface.freshtv.commonandroid.BuildConfig.DEBUG

import timber.log.Timber

/**
 * A custom [Timber.DebugTree]
 *
 * @author Davide Giuseppe Farella
 */
class TimberTree: Timber.DebugTree() {

    /**
     * Whether a message at `priority` or `tag` should be logged
     * @return `true` if [DEBUG] or [priority] is equal or greater than [Log.WARN]
     */
    override fun isLoggable( tag: String?, priority: Int ) = DEBUG || priority >= Log.WARN

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See [Log] for constants.
     * @param tag Explicit or inferred tag. May be `null`.
     * @param message Formatted log message. May be `null`, but then [t] will not be.
     * @param t Accompanying exceptions. May be `null`, but then [message] will not be.
     */
    override fun log( priority: Int, tag: String?, message: String, t: Throwable? ) {
        super.log( priority, tag, message, t )

        // Log if not DEBUG.
        // Note that the log function will be called only if is DEBUG or level equal or greater to Log.WARN
        if ( ! DEBUG ) logToCrashlytics( priority, tag, message, t )
    }

    /** Write log to Crashlytics */
    private fun logToCrashlytics( priority: Int, tag: String?, message: String, t: Throwable? ) {
        t?.let { Crashlytics.logException( it ) }
            ?: Crashlytics.log( priority, tag, message )
    }
}