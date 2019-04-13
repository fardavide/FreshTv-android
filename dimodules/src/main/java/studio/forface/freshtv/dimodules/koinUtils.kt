package studio.forface.freshtv.dimodules

import org.koin.core.module.Module

/** @return [List] of [Module]s of the receiver [Module] plus [others] */
operator fun Module.plus( others: List<Module> ) = listOf( this ) + others