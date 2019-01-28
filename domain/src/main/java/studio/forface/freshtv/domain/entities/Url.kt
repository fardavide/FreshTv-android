package studio.forface.freshtv.domain.entities

/**
 * @author Davide Giuseppe Farella.
 * An inline class for [String] url.
 */
inline class Url( val s: String ): Validable {

    /** A constructor that accepts a [CharSequence] */
    constructor( c: CharSequence ): this( c.toString() )

    /** @see Validable.validate */
    override fun validate(): Validable.Result {
        return when {
            s.isBlank() -> Failure.Empty
            s.startsWith("http" ) && s.contains('.' ) -> Validable.Result.Success
            else -> Failure.BadFormat
        }
    }

    /** @see Validable.Result.Failure */
    sealed class Failure : Validable.Result.Failure() {
        object Empty :      Url.Failure()
        object BadFormat :  Url.Failure()
    }
}