package studio.forface.freshtv.commonandroid.notifier

import android.util.Log
import com.crashlytics.android.Crashlytics

import timber.log.Timber

/**
 * @author Davide Giuseppe Farella.
 * A custom [Timber.DebugTree]
 */
class TimberTree: Timber.DebugTree() {

    /** @return [Boolean] whether a message at `priority` or `tag` should be logged.  */
    override fun isLoggable( tag: String?, priority: Int ): Boolean = true

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See [Log] for constants.
     * @param tag Explicit or inferred tag. May be `null`.
     * @param message Formatted log message. May be `null`, but then `t` will not be.
     * @param t Accompanying exceptions. May be `null`, but then `message` will not be.
     */
    override fun log( priority: Int, tag: String?, message: String, t: Throwable? ) {
        super.log( priority, tag, message, t )

        t?.let { Crashlytics.logException( it ) }
            ?: Crashlytics.log( priority, tag, message )
    }
}