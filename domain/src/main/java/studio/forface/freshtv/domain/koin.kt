package studio.forface.freshtv.domain

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import studio.forface.freshtv.domain.usecases.*

/** A [Module] that handles dependencies for use cases of `domain` module */
val useCases = module {
    factory { AddChannelMediaUrl( localData = get() ) }
    factory { IncrementChannelMediaFailure( localData = get() ) }
    factory { RemoveChannelMediaUrl( localData = get() ) }
    factory { RenameChannel( localData = get() ) }
    factory { ResetChannelMediaFailure( localData = get() ) }
}