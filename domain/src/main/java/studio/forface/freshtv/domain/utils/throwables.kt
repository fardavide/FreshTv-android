package studio.forface.freshtv.domain.utils

/*
 * Author: Davide Giuseppe Farella.
 * An utilities file for Throwable's.
 */

/**
 * Get a [String] message that can never be null.
 *
 * @return [String]
 *
 * @see Throwable.getLocalizedMessage
 * @see Throwable.message
 * @see EMPTY_STRING
 */
val Throwable.nonNullMessage: String get() = localizedMessage ?: message ?: EMPTY_STRING