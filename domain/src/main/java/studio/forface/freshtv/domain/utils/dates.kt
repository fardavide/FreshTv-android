package studio.forface.freshtv.domain.utils

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author Davide Giuseppe Farella.
 * Utilities for [LocalDateTime].
 */
object LocalDateTimeHelper {

    /** @return the local [ZoneOffset] */
    val localOffset get(): ZoneOffset =
            ZoneOffset.systemDefault().rules.getOffset( Instant.now() )

    /** @return [LocalDateTime.ofEpochSecond] with default value for [nanoOfSecond] and [offset] */
    fun ofEpochSecond( epochSecond: Long, nanoOfSecond: Int = 0, offset: ZoneOffset = ZoneOffset.UTC ): LocalDateTime =
            LocalDateTime.ofEpochSecond( epochSecond, nanoOfSecond, offset )

    /** @return [LocalDateTime.ofEpochSecond] with an epoch in milliseconds */
    fun ofEpochMillis( epochMillis: Long, offset: ZoneOffset = ZoneOffset.UTC ): LocalDateTime {
        val epochSecond = epochMillis / 1000
        val nanoOfSecond = ( ( epochMillis % 1000 ) * 1_000_000 ).toInt()
        return LocalDateTime.ofEpochSecond( epochSecond, nanoOfSecond, offset )
    }
}

/** @return a [Duration] equivalent to the given [Int] of days */
val Int.days: Duration get() = Duration.ofDays( toLong() )

/** @return a [Duration] equivalent to the given [Int] of hours */
val Int.hours: Duration get() = Duration.ofHours( toLong() )

/** @return a [Duration] equivalent to the given [Int] of seconds */
val Int.seconds: Duration get() = Duration.ofSeconds( toLong() )

/** @return a [BackDuration] equivalent to the given [Int] of days */
infix fun Int.days( ago: ago ) = BackDuration( this.days )

/** @return a [BackDuration] equivalent to the given [Int] of hours */
infix fun Int.hours( ago: ago ) = BackDuration( this.hours )

/** @return a [BackDuration] equivalent to the given [Int] of seconds */
infix fun Int.seconds( ago: ago ) = BackDuration( this.seconds )

/** An "empty" object for create a [BackDuration] via infix functions */
object ago

/** A class that holds a [Duration] and can be invoked for retrieve the current time `minus` the [Duration] */
class BackDuration internal constructor( private val duration: Duration ) {
    /** @return a [LocalDateTime] represented by the current time `minus` [duration] */
    operator fun invoke(): LocalDateTime = LocalDateTime.now().minus( duration )
}

/**
 * Serialize a [LocalDate] into a [String].
 * @return [String].
 */
fun LocalDate.serialize( formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE ): String =
    format( formatter )

/**
 * Serialize a [LocalDateTime] into a [String].
 * @return [String].
 */
fun LocalDateTime.serialize( formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME ): String =
    format( formatter )

/** Call [LocalDateTime.toEpochSecond] using the [ZoneOffset.UTC] */
fun LocalDateTime.toEpochSecond() = toEpochSecond( ZoneOffset.UTC )

/** @return a [Long] representing the epoch in milliseconds */
fun LocalDateTime.toEpochMillis( offset: ZoneOffset = ZoneOffset.UTC ): Long {
    val seconds = toEpochSecond( offset ) * 1000
    val millis = nano / 1_000_000
    return seconds + millis
}