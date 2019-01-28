package studio.forface.freshtv.domain.exceptions

import studio.forface.freshtv.domain.entities.Validable
import kotlin.reflect.KClass

/**
 * @author Davide Giuseppe Farella.
 * An Exception that indicated that a [Validable] has not validated correctly.
 * @see Validable.validate
 *
 * Inherit from [IllegalStateException].
 */
class ValidationException(
    entityClass: KClass<out Validable>,
    override val message: String = "${entityClass.qualifiedName} has not been validated correctly"
): IllegalStateException()