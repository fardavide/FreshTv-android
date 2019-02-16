@file:Suppress("unused")

package studio.forface.freshtv.commonandroid.utils

/*
 * Author: Davide Giuseppe Farella
 * Utilities for Collections
 */

/** @return [Array.joinToString] with [T] [toString] as transformation */
fun <T> Array<T>.joinToString() = joinToString { it.toString() }

/** @return [Iterable.joinToString] with [E] [toString] as transformation */
fun <E> Iterable<E>.joinToString() = joinToString { it.toString() }