package studio.forface.freshtv.localdata.mappers

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map

/**
 * @author Davide Giuseppe Farella.
 * An common interface for transform an Entity to a Pojo and transform back a Pojo to an entity.
 *
 * @param P the type of the Pojo
 * @param E the type of the entity
 */
interface PojoMapper<E, P> {

    /** Create a Pojo [P] from the given entity [E] */
    fun E.toPojo(): P

    /** Create an entity [E] from the given Pojo [P] */
    fun P.toEntity(): E
}

/**
 * Override of invoke operator for get access to `this` [PojoMapper] as receiver of the lambda for call extension
 * functions declared in this class:
 * e.g. `userMapper { registrationParams.toPojo() }`
 */
inline operator fun <E, P, T> PojoMapper<E, P>.invoke( f: PojoMapper<E, P>.() -> T ) = f()

/**
 * Call [Collection.map] passing a [PojoMapper] as receiver for the lambda [block]
 *
 * E.g. `myListOfT.map( myTMapper ) { it.toPojo() }`
 */
inline fun <T, V, E, P, M: PojoMapper<E, P>> Collection<T>.map(
    mapper: M,
    block: M.(T) -> V
) = map { mapper.block( it ) }

/**
 * Call [ReceiveChannel.map] passing a [PojoMapper] as receiver for the lambda [block]
 *
 * E.g. `myChannel.map( myTMapper ) { it.toEntity() }`
 */
inline fun <T, V, E, P, M: PojoMapper<E, P>> ReceiveChannel<T>.map(
    mapper: M,
    crossinline block: M.(T) -> V
) = map { mapper.block( it ) }