package studio.forface.freshtv.parsers

import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.gateways.Parsers

/** A [Module] that handles dependencies for `parsers` module */
val parsersModule = module {
    factory<Parsers> { ParsersImpl() }
}