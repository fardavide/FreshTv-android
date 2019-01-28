package studio.forface.freshtv.domain.exceptions

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import studio.forface.freshtv.domain.entities.IChannel

/**
 * @author Davide Giuseppe Farella.
 * An Exception for a subtype of [IChannel] not implemented for the given method in the given class.
 *
 * Inherit from [Error]
 *
 * @param klass the [KClass] throwing the Exception
 * @param method the [KFunction] throwing the Exception
 * @param type the subtype of [IChannel] not implemented
 */
class ChannelNotImplementedError( klass: KClass<*>, method: KFunction<*>, type: KClass<*> ) :
        Error( "${type.simpleName} not implemented for ${klass.qualifiedName}.${method.name}" )