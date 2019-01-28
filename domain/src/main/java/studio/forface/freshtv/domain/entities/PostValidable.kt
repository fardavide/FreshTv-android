package studio.forface.freshtv.entities;

/** @author Davide Giuseppe Farella.
 * An interface for entities that can be validated.
 */
interface PostValidable<T>: Validable {

    /**
     * Fix the format of the entity
     */
    fun postValidate() : T
}