package studio.forface.freshtv.domain.utils

/**
 * A shortcut for a try/catch block with an optional return value.
 * @param default the value that will be returned if an exception is thrown. Default is null.
 *
 * Examples:
 * >    val number: Int = handle( 0 ) { string.toInt() }
 *      val number: Int? = handle { string.toInt() } // if exception, 'number' will be null
 *
 * @return OPTIONAL [T]
 */
fun <T: Any?> handle( default: T? = null, block: () -> T ) : T? {
    return  try {
        block()
    } catch ( t: Throwable ) {
        default
    }
}

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
infix fun <T: Any> T?.or( block: () -> T ) : T = this ?: block()