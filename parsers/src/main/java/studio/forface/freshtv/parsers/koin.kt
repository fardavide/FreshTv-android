package studio.forface.freshtv.parsers

import org.koin.dsl.module.module
import org.koin.dsl.module.Module
import studio.forface.freshtv.domain.gateways.Parsers

/** A [Module] that handles dependencies for `playlistsource` module */
val playlistSource = module {
    factory<Parsers> { ParsersImpl() }
}