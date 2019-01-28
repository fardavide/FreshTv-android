package studio.forface.freshtv.domain.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime as ThreeTenLocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author Davide Giuseppe Farella.
 * Utilities for [ThreeTenLocalDateTime].
 */
object LocalDateTime {

    /**
     * GET the local [ZoneOffset].
     * # TESTED.
     */
    val localOffset get(): ZoneOffset =
        ZoneOffset.systemDefault().rules.getOffset( Instant.now() )

    /** @return [ThreeTenLocalDateTime.ofEpochSecond] with [localOffset] */
    fun ofEpochSecond( epochSecond: Long, nanoOfSecond: Int = 0 ): ThreeTenLocalDateTime =
            ThreeTenLocalDateTime.ofEpochSecond( epochSecond, nanoOfSecond, localOffset )
}

/**
 * Serialize a [LocalDate] into a [String].
 * @return [String].
 */
fun LocalDate.serialize(formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE ): String =
    format( formatter )

/**
 * Serialize a [ThreeTenLocalDateTime] into a [String].
 * @return [String].
 */
fun ThreeTenLocalDateTime.serialize(formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME ): String =
    format( formatter )

/** Call [ThreeTenLocalDateTime.toEpochSecond] using the [LocalDateTime.localOffset] */
fun ThreeTenLocalDateTime.toEpochSecond() = toEpochSecond( LocalDateTime.localOffset )