package studio.forface.freshtv.domain

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.usecases.*

/** A [Module] that handles dependencies for use cases of `domain` module */
val useCasesModule = module {

    /* Add */
    factory { AddChannelMediaUrl( localData = get() ) }
    factory { AddEpg( localData = get() ) }
    factory { AddPlaylist( localData = get() ) }

    /* Delete */
    factory { DeleteOldGuides( localData = get(), settings = get() ) }

    /* Get */
    factory { GetCurrentTvGuide( localData = get() ) }
    factory { GetMovieChannelGroups( localData = get() ) }
    factory { GetMovieChannels( localData = get() ) }
    factory { GetPlaylist( localData = get() ) }
    factory { GetPlaylists( localData = get() ) }
    factory { GetTvChannelGroups( localData = get() ) }
    factory { GetTvChannels( localData = get() ) }

    /* Has */
    factory { HasMovieChannels( localData = get() ) }
    factory { HasTvChannels( localData = get() ) }

    /* Increment */
    factory { IncrementChannelMediaFailure( localData = get() ) }

    /* Refresh */
    factory { RefreshChannels( localData = get(), parsers = get() ) }
    factory { RefreshTvGuides( localData = get(), parsers = get() ) }

    /* Remove */
    factory { RemoveChannelMediaUrl( localData = get() ) }
    factory { RemoveEpg( localData = get() ) }
    factory { RemovePlaylist( localData = get() ) }

    /* Rename */
    factory { RenameChannel( localData = get() ) }

    /* Reset */
    factory { ResetChannelMediaFailure( localData = get() ) }

    /* Update */
    factory { UpdateChannelFavoriteState( localData = get() ) }
    factory { UpdateEpg( localData = get() ) }
    factory { UpdatePlaylist( localData = get() ) }
}