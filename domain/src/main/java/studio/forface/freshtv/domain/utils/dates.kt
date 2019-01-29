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

    /**
     * GET the local [ZoneOffset].
     * # TESTED.
     */
    val localOffset get(): ZoneOffset =
        ZoneOffset.systemDefault().rules.getOffset( Instant.now() )

    /** @return [LocalDateTime.ofEpochSecond] with [ZoneOffset.UTC] */
    fun ofEpochSecond( epochSecond: Long, nanoOfSecond: Int = 0 ): LocalDateTime =
            LocalDateTime.ofEpochSecond( epochSecond, nanoOfSecond, ZoneOffset.UTC )
}

/**
 * Serialize a [LocalDate] into a [String].
 * @return [String].
 */
fun LocalDate.serialize(formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE ): String =
    format( formatter )

/**
 * Serialize a [LocalDateTime] into a [String].
 * @return [String].
 */
fun LocalDateTime.serialize(formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME ): String =
    format( formatter )

/** Call [LocalDateTime.toEpochSecond] using the [ZoneOffset.UTC] */
fun LocalDateTime.toEpochSecond() = toEpochSecond( ZoneOffset.UTC )