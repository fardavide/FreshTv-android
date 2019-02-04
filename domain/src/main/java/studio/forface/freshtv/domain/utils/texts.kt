package studio.forface.freshtv.domain.utils

const val EMPTY_STRING = ""

/** @return null if the receiver [CharSequence.isBlank] else the receiver itself */
fun CharSequence.notBlankOrNull() = if ( isBlank() ) null else this

/** @return null if the receiver [String.isBlank] else the receiver itself */
fun String.notBlankOrNull() = if ( isBlank() ) null else this

/** @return [T] parsed from the receiver [String] */
@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T> String?.to(): T {
    val klass = T::class
    val nullable = null is T
    return when {
        this == null && nullable -> null
        this == null -> throw IllegalStateException(
                "The String is null, but ${klass.qualifiedName} is not nullable"
        )

        klass == String::class -> this

        klass == Int::class && nullable -> toIntOrNull()
        klass == Int::class -> toInt()

        klass == Long::class && nullable -> toLongOrNull()
        klass == Long::class -> toLong()

        else -> throw NotImplementedError( "${klass.qualifiedName} not implemented" )
    } as T
}