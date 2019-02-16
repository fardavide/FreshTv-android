package studio.forface.freshtv.entities

import studio.forface.freshtv.domain.entities.Validable

/**
 * @author Davide Giuseppe Farella.
 * An inline class for [String] path on `SourceFile`.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class SourceFilePath( private val s: String ): Validable {

    /** A constructor that accepts a [CharSequence] */
    constructor( c: CharSequence ): this( c.toString() )

    /** @see Validable.validate */
    override fun validate(): Validable.Result {
        return when {
            s.isBlank() -> Failure.Empty
            s.contains("://" ) && s.filter { it == '/' }.length > 3 ->
                Validable.Result.Success
            else -> Failure.BadFormat
        }
    }

    /** @see Validable.Result.Failure */
    sealed class Failure : Validable.Result.Failure() {
        object Empty :      SourceFilePath.Failure()
        object BadFormat :  SourceFilePath.Failure()
    }
}