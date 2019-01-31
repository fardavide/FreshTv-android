package studio.forface.freshtv.domain

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import studio.forface.freshtv.domain.usecases.*

/** A [Module] that handles dependencies for use cases of `domain` module */
val useCases = module {
    factory { AddChannelMediaUrl( localData = get() ) }
    factory { AddPlaylist( localData = get() ) }
    factory { IncrementChannelMediaFailure( localData = get() ) }
    factory { RefreshPlaylists( localData = get(), playlistSource = get() ) }
    factory { RemoveChannelMediaUrl( localData = get() ) }
    factory { RemovePlaylist( localData = get() ) }
    factory { RenameChannel( localData = get() ) }
    factory { ResetChannelMediaFailure( localData = get() ) }
}