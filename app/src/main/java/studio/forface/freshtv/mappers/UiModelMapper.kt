package studio.forface.freshtv.mappers

/**
 * @author Davide Giuseppe Farella.
 * An common interface for transform an Entity to an UiModel and transform back an UiModel to an
 * entity.
 *
 * @param UI the type of the UiModel
 * @param E the type of the entity
 */
abstract class UiModelMapper<E, UI> {

    /**
     *  Override of invoke operator for get access to `this` [UiModelMapper] as receiver of the
     *  lambda for call extension functions declared in this class:
     *  e.g. `userMapper { registrationParams.toUiModel() }`
     */
    inline operator fun <T> invoke( f: UiModelMapper<E, UI>.() -> T ) = f()

    /** Create an UiModel [UI] from the given entity [E] */
    abstract fun E.toUiModel(): UI

    /** Create an entity [E] from the given UiModel [UI] */
    abstract fun UI.toEntity(): E
}