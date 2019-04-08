package studio.forface.freshtv.domain.gateways

import studio.forface.freshtv.domain.utils.nonNullMessage

/**
 * @author Davide Giuseppe Farella.
 * A Service for notify an event to the user.
 */
interface Notifier<B : Notifier.ActionBuilder> {

    /** Show an error event */
    fun error(
        throwable: Throwable,
        message: CharSequence = throwable.nonNullMessage,
        optionalAction: OptionalAction<B> = null
    )

    /** Show an error event */
    fun error( message: CharSequence, optionalAction: OptionalAction<B> = null )

    /** Show an info event */
    fun info(
        throwable: Throwable,
        message: CharSequence = throwable.nonNullMessage,
        optionalAction: OptionalAction<B> = null
    )

    /** Show an info event */
    fun info( message: CharSequence, optionalAction: OptionalAction<B> = null )

    /** Show a warn event */
    fun warn(
        throwable: Throwable,
        message: CharSequence = throwable.nonNullMessage,
        optionalAction: OptionalAction<B> = null
    )

    /** Show a warn event */
    fun warn( message: CharSequence, optionalAction: OptionalAction<B> = null )

    /** A data class that represent an action that could be appended to the message to show */
    data class Action ( val name: CharSequence, val block: () -> Unit )

    /** A builder for create [Action] within a DSL style */
    abstract class ActionBuilder {
        /** @see Action.name */
        var actionName: CharSequence? = null
        /** @see Action.block */
        var actionBlock: () -> Unit = {}

        /**
         * @return [Action] created from [actionName] and [actionBlock]
         * @throws KotlinNullPointerException if [actionName] has not been set.
         */
        abstract fun build(): Action
    }
}

/**
 * A typealias for [Notifier] representing an OPTIONAL lambda with [Notifier.ActionBuilder] as received, that returns
 * [Unit]
 */
typealias OptionalAction<T> = ( T.() -> Unit )?