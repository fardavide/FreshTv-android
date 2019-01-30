package studio.forface.freshtv.domain.entities

import studio.forface.freshtv.domain.errors.ValidationException

/**
 * @author Davide Giuseppe Farella.
 * An interface for entities that can be validated.
 */
interface Validable {

    /** @return true if [validate] return [Result.Success] */
    val valid get() = validate() is Result.Success

    /**
     * Validate the entity.
     * @return [Validable.Result]
     */
    fun validate() : Result

    /**
     * @return the entity itself if valid, else null
     * @see valid
     */
    @Suppress("UNCHECKED_CAST")
    fun <V: Validable> validOrNull() = if ( valid ) ( this as V ) else null

    /** A sealed class for a result of [validate] */
    sealed class Result {
        object Success : Result()
        abstract class Failure : Result()
    }
}


/**
 * An interface for entities that can be validated.
 * Inherit from [Validable]
 */
interface PostValidable : Validable {

    /**
     * Fix the format of the entity
     */
    fun <V: PostValidable> postValidate() : V
}


/**
 * Assert that [PostValidable.validate] is [Validable.Result.Success].
 * @throws ValidationException
 */
internal fun <V: PostValidable> V.assertValid() {
    if ( validate() !is Validable.Result.Success ) throw ValidationException(
        this::class
    )
}