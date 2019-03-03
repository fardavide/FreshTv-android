package studio.forface.freshtv.commonandroid.utils

import org.threeten.bp.format.DateTimeFormatter

/**
 * Author: Davide Giuseppe Farella.
 * Utilities for `LocalDateTime`.
 */

/** @return a default [DateTimeFormatter] for Date and Time */
val defaultDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

/** @return a default [DateTimeFormatter] for Time only */
val defaultTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME