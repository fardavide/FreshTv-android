package studio.forface.freshtv.domain.utils

import org.threeten.bp.Duration

/**
 * A shortcut for a try/catch block, as expression or statement, with an optional return value.
 * @param default the value that will be returned if an exception is thrown. Default is null.
 *
 * Examples:
 * >    val number: Int = handle( 0 ) { string.toInt() }
 *      val number: Int? = handle { string.toInt() } // if exception, 'number' will be null
 *
 * @return OPTIONAL [T]
 */
inline fun <T: Any?> handle( default: T? = null, block: () -> T ) : T? {
    return try { block() }
    catch ( t: Throwable ) { default }
}

/** An interface for entities that have a default [invoke] operator */
interface Invokable

/** Invoke operator */
inline operator fun <T : Invokable, V> T.invoke( block: T.() -> V ) = block()

/** Same as [optWith], but pass [T] as argument */
inline fun <T: Any, V> optLet( arg: T?, block: (T) -> V ) =
        arg?.let { block( arg ) }

/** Same as `with`, but execute [block] only if [receiver] is not null */
inline fun <T: Any, V> optWith( receiver: T?, block: T.() -> V ) =
        receiver?.let { block( receiver ) }

/**
 * An infix function for use `or` instead of the elvis operator ( `?:` ).
 * Its main purpose is to have a non-lambda counterpart of [or] with a lambda block.
 *
 * @return NOT NULL [T].
 */
infix fun <T: Any> T?.or( other: T ) : T = this ?: other

/**
 * An infix function for use `or` instead of the elvis operator ( `?:` ) with a lambda.
 * @return NOT NULL [T].
 */
inline infix fun <T: Any> T?.or( block: () -> T ) : T = this ?: block()

/** Call [kotlinx.coroutines.delay] with [Duration] */
suspend fun delay( duration: Duration ) {
    kotlinx.coroutines.delay( duration.toMillis() )
}

/** Wait until [evaluation] is true */
inline fun wait( evaluation: () -> Boolean ) {
    while( ! evaluation() ) { /* wait */ }
}