package studio.forface.freshtv.localdata.mappers

/**
 * @author Davide Giuseppe Farella.
 * An common interface for transform an Entity to a Pojo and transform back a Pojo to an entity.
 *
 * @param P the type of the Pojo
 * @param E the type of the entity
 */
abstract class PojoMapper<E, P> {

    /**
     *  Override of invoke operator for get access to `this` [PojoMapper] as receiver of the lambda for call extension
     *  functions declared in this class:
     *  e.g. `userMapper { registrationParams.toPojo() }`
     */
    inline operator fun <T> invoke( f: PojoMapper<E, P>.() -> T ) = f()

    /** Create a Pojo [P] from the given entity [E] */
    abstract fun E.toPojo(): P

    /** Create an entity [E] from the given Pojo [P] */
    abstract fun P.toEntity(): E
}