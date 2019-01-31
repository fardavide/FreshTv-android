package studio.forface.freshtv.domain

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import studio.forface.freshtv.domain.usecases.*

/** A [Module] that handles dependencies for use cases of `domain` module */
val useCases = module {

    /* Add */
    factory { AddChannelMediaUrl( localData = get() ) }
    factory { AddPlaylist( localData = get() ) }

    /* Get */
    factory { GetMovieChannelGroups( localData = get() ) }
    factory { GetMovieChannels( localData = get() ) }
    factory { GetTvChannelGroups( localData = get() ) }
    factory { GetTvChannels( localData = get() ) }

    /* Increment */
    factory { IncrementChannelMediaFailure( localData = get() ) }

    /* Refresh */
    factory { RefreshPlaylists( localData = get(), playlistSource = get() ) }

    /* Remove */
    factory { RemoveChannelMediaUrl( localData = get() ) }
    factory { RemovePlaylist( localData = get() ) }

    /* Rename */
    factory { RenameChannel( localData = get() ) }

    /* Reset */
    factory { ResetChannelMediaFailure( localData = get() ) }
}