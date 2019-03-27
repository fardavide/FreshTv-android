package studio.forface.freshtv.domain

/** A typealias of [Nothing] for unsupported operations */
typealias Unsupported = Nothing

/**
 * [Unsupported]
 * @throws UnsupportedOperationException
 */
val unsupported: Nothing get() = throw UnsupportedOperationException( "unsupported" )

/** Call [invoke] on lambda that takes [Unit} and returns [Unit}, without explicitly pass [Unit] */
operator fun ( (Unit) -> Unit ).invoke() = invoke( Unit )