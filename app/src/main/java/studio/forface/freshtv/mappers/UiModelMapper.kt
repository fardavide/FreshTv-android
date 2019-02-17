package studio.forface.freshtv.mappers

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

    /**
     *  Override of invoke operator for get access to `this` [UiModelMapper] as receiver of the
     *  lambda for call extension functions declared in this class:
     *  e.g. `userMapper { registrationParams.toUiModel() }`
     */
    inline operator fun <T> invoke( f: UiModelMapper<Ein, UI, Eout>.() -> T ) = f()

    /** Create an UiModel [UI] from the given entity [Ein] */
    abstract fun Ein.toUiModel(): UI

    /** Create an entity [Eout] from the given UiModel [UI] */
    abstract fun UI.toEntity(): Eout
}

/**
 * A typealias of [Unit] for unsupported mapping; usually for an unsupported mapping from `UiModel`
 * to `Entity`
 */
internal typealias Unsupported = Unit