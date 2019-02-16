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
        backoffDelay: Duration = 30.seconds
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

    builder.setBackoffCriteria( backoffPolicy, backoffDelay.toMinutes(), timeUnit )

    enqueueUniquePeriodicWork( uniqueWorkName, replacePolicy, builder.build() )
}

/** Enqueue an unique periodic Work without any optional parameters */
inline fun <reified W: ListenableWorker> WorkManager.enqueueUniqueWork(
        uniqueWorkName: String,
        replacePolicy: ExistingWorkPolicy = ExistingWorkPolicy.APPEND,
        exponentialBackoff: Boolean = true,
        backoffDelay: Duration = 30.seconds
) {
    val timeUnit = TimeUnit.MINUTES
    val backoffPolicy = if ( exponentialBackoff ) EXPONENTIAL else LINEAR

    val builder = OneTimeWorkRequestBuilder<W>()
            .setBackoffCriteria( backoffPolicy, backoffDelay.toMinutes(), timeUnit )

    enqueueUniqueWork( uniqueWorkName, replacePolicy, builder.build() )
}