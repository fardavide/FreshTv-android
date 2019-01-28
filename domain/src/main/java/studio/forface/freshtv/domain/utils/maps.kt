package studio.forface.freshtv.domain.utils

/** Decrement the value of a [MutableMap] that has [Int] as [Map.values], if it isn't null, else does nothing */
infix fun <K: Any> MutableMap<K, Int>.decrement( key: K ) {
    this[key]?.let { value -> this[key] = value - 1 }
}

/**
 * Decrement the value of a [MutableMap] that has [Int] as [Map.values], if it isn't null, else create the key and
 * assign -1 ( 0 - 1 )
 */
infix fun <K: Any> MutableMap<K, Int>.decrementOrCreate( key: K ) {
    this[key] = ( this[key] ?: 0 ) - 1
}

/**
 * @return a new instance of the the [Map] with increment the value of a [MutableMap] that has [Int] as [Map.values],
 * if it isn't null, else does nothing
 */
infix fun <K: Any> Map<K, Int>.increment( key: K ): Map<K, Int> =
        toMutableMap().apply { increment( key ) }

/** Increment the value of a [MutableMap] that has [Int] as [Map.values], if it isn't null, else does nothing */
infix fun <K: Any> MutableMap<K, Int>.increment( key: K ) {
    this[key]?.let { value -> this[key] = value + 1 }
}

/**
 * Increment the value of a [MutableMap] that has [Int] as [Map.values], if it isn't null, else create the key and
 * assign 1 ( 0 + 1 )
 */
infix fun <K: Any> MutableMap<K, Int>.incrementOrCreate( key: K ) {
    this[key] = ( this[key] ?: 0 ) + 1
}

/**
 * @return a new instance of the the [Map] with aAdd an entry with the given [key] and a value of 0 to a [MutableMap]
 * that has [Int] as [Map.values]
 */
operator fun <K: Any> Map<K, Int>.plus( key: K ): Map<K, Int> =
    toMutableMap().apply { this.plusAssign( key ) }

/** Add an entry with the given [key] and a value of 0 to a [MutableMap] that has [Int] as [Map.values] */
operator fun <K: Any> MutableMap<K, Int>.plusAssign( key: K ) {
    this[key] = 0
}

/**
 * @return a new instance of the the [Map] with reset the value of a [Map] that has [Int] as [Map.values], if it isn't
 * null, else does nothing
 */
infix fun <K: Any> Map<K, Int>.reset( key: K ): Map<K, Int> =
    toMutableMap().apply { this.reset( key ) }

/** Reset the value of a [MutableMap] that has [Int] as [Map.values], if it isn't null, else does nothing */
infix fun <K: Any> MutableMap<K, Int>.reset( key: K ) {
    this[key]?.let { this[key] = 0 }
}

/**
 * Reset the value of a [MutableMap] that has [Int] as [Map.values], if it isn't null, else create the key and
 * assign 0
 */
infix fun <K: Any> MutableMap<K, Int>.resetOrCreate( key: K ) {
    this[key] = 0
}