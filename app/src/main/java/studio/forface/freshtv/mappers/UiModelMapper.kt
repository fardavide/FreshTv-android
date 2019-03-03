package studio.forface.freshtv.mappers

import androidx.paging.DataSource

/**
 * @author Davide Giuseppe Farella.
 * An common interface for transform an Entity to an UiModel and transform back an UiModel to an
 * entity.
 *
 * @param Ein the type of the source entity
 * @param UI the type of the UiModel
 * @param Eout the type of the entity generated
 */
abstract class UiModelMapper<in Ein, UI, out Eout> {

    /** Create an UiModel [UI] from the given entity [Ein] */
    abstract fun Ein.toUiModel(): UI

    /** Create an entity [Eout] from the given UiModel [UI] */
    abstract fun UI.toEntity(): Eout
}

/**
 * A typealias of [Nothing] for unsupported mapping; usually for an unsupported mapping from `UiModel`
 * to `Entity`
 */
internal typealias Unsupported = Nothing

/** A `null` value casted as [Nothing] - [Unsupported] */
@Suppress("CAST_NEVER_SUCCEEDS")
internal val unsupported: Nothing = null as Nothing

/**
 * Override of invoke operator for get access to `this` [UiModelMapper] as receiver of the
 * lambda for call extension functions declared in this class:
 * e.g. `userMapper { registrationParams.toUiModel() }`
 */
inline operator fun <T, Ein, UI, Eout, M: UiModelMapper<Ein, UI, Eout>> M.invoke( f: M.() -> T ) = f()


/**
 * Call [DataSource.Factory.map] passing a [UiModelMapper] as receiver for the lambda [block]
 *
 * E.g. `myDataSourceOfT.map( myTMapper ) { it.toEntity() }`
 */
inline fun <T, V, Ein, UI, Eout, M: UiModelMapper<Ein, UI, Eout>> DataSource.Factory<Int, T>.map(
    mapper: M,
    crossinline block: M.(T) -> V
) = map { mapper.block( it ) }

/**
 * Call [Collection.map] passing a [UiModelMapper] as receiver for the lambda [block]
 *
 * E.g. `myListOfT.map( myTMapper ) { it.toEntity() }`
 */
inline fun <I, O, M: UiModelMapper<I, O, *>> Collection<I>.map(
    mapper: M,
    block: M.(I) -> O
) = map { mapper.block( it ) }
