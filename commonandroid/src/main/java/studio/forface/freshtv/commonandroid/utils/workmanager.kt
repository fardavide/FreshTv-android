package studio.forface.freshtv.commonandroid.utils

import androidx.work.*
import androidx.work.BackoffPolicy.*
import org.threeten.bp.Duration
import studio.forface.freshtv.domain.utils.days
import studio.forface.freshtv.domain.utils.seconds
import java.util.concurrent.TimeUnit

/*
 * Author: Davide Giuseppe Farella.
 * Utilities for WorkManager
 */

/** @return an instance of [WorkManager] by [WorkManager.getInstance] */
val workManager get() = WorkManager.getInstance()

/** Enqueue an unique periodic Work without any optional parameters */
inline fun <reified W: ListenableWorker> WorkManager.enqueueUniquePeriodicWork(
    uniqueWorkName: String,
    repeatInterval: Duration,
    flexTimeInterval: Duration? = null,
    replace: Boolean = false,
    exponentialBackoff: Boolean = true,
    backoffDelay: Duration = 30.seconds,
    constraints: Constraints = Constraints.NONE,
    workData: Data? = null
) {
    val timeUnit = TimeUnit.MINUTES
    val repeatMin = repeatInterval.toMinutes()
    val flexMin = flexTimeInterval?.toMinutes()
    val replacePolicy = if ( replace )
        ExistingPeriodicWorkPolicy.REPLACE else ExistingPeriodicWorkPolicy.KEEP
    val backoffPolicy = if ( exponentialBackoff ) EXPONENTIAL else LINEAR

    val builder = if ( flexMin != null )
        PeriodicWorkRequestBuilder<W>( repeatMin, timeUnit, flexMin, timeUnit )
    else PeriodicWorkRequestBuilder<W>( repeatMin, timeUnit )

    builder.apply {
        // Set backoff criteria only if IDLE is not required, due to "Cannot set backoff criteria on an idle mode job"
        if ( ! Android.MARSHMALLOW || ! constraints.requiresDeviceIdle() )
            setBackoffCriteria( backoffPolicy, backoffDelay.toMinutes(), timeUnit )
        setConstraints( constraints )
        workData?.let { setInputData( it ) }
    }

    enqueueUniquePeriodicWork( uniqueWorkName, replacePolicy, builder.build() )
}

/** Enqueue an unique periodic Work without any optional parameters */
inline fun <reified W: ListenableWorker> WorkManager.enqueueUniqueWork(
        uniqueWorkName: String,
        replacePolicy: ExistingWorkPolicy = ExistingWorkPolicy.APPEND,
        exponentialBackoff: Boolean = true,
        backoffDelay: Duration = 30.seconds,
        constraints: Constraints = Constraints.NONE,
        workData: Data? = null
) {
    val timeUnit = TimeUnit.MINUTES
    val backoffPolicy = if ( exponentialBackoff ) EXPONENTIAL else LINEAR

    val builder = OneTimeWorkRequestBuilder<W>().apply {
        // Set backoff criteria only if IDLE is not required, due to "Cannot set backoff criteria on an idle mode job"
        if ( ! Android.MARSHMALLOW || ! constraints.requiresDeviceIdle() )
            setBackoffCriteria( backoffPolicy, backoffDelay.toMinutes(), timeUnit )
        setConstraints( constraints )
        workData?.let { setInputData( it ) }
    }

    enqueueUniqueWork( uniqueWorkName, replacePolicy, builder.build() )
}

/** @return [Constraints] invoking lambda [block] with [Constraints.Builder] as receiver */
@Suppress("FunctionName")
fun WorkConstraints(block: Constraints.Builder.() -> Unit ): Constraints {
    return with( Constraints.Builder() ) {
        block()
        build()
    }
}