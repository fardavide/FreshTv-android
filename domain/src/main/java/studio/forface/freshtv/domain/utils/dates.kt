package studio.forface.freshtv.domain.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
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
    fun ofEpochSecond( epochSecond: Long, nanoOfSecond: Int = 0, offset: ZoneOffset = ZoneOffset.UTC ) =
            LocalDateTime.ofEpochSecond( epochSecond, nanoOfSecond, offset )

    /** @return [LocalDateTime.ofEpochSecond] with an epoch in milliseconds */
    fun ofEpochMillis( epochMillis: Long, offset: ZoneOffset = ZoneOffset.UTC ): LocalDateTime {
        val epochSecond = epochMillis / 1000
        val nanoOfSecond = ( ( epochMillis % 1000 ) * 1_000_000 ).toInt()
        return LocalDateTime.ofEpochSecond( epochSecond, nanoOfSecond, offset )
    }
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