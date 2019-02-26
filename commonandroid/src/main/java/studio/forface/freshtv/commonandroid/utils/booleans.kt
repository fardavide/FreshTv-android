package studio.forface.freshtv.commonandroid.utils

/*
 * Author: Davide Giuseppe Farella
 * Utils for `Boolean`s
 */

/** @return the [T] result of [block] if Boolean is `true`, else `null` */
operator fun <T> Boolean.invoke( block: () -> T ) = if ( this ) block() else null