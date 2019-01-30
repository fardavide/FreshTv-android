package studio.forface.freshtv.playlistsource

import org.koin.dsl.module.module
import org.koin.dsl.module.Module
import studio.forface.freshtv.domain.gateways.PlaylistSource

/** A [Module] that handles dependencies for `playlistsource` module */
val playlistSource = module {
    factory<PlaylistSource> { PlaylistSourceImpl() }
}