package studio.forface.freshtv.dimodules

import org.koin.core.module.Module
import studio.forface.freshtv.androiddatabase.androidDatabaseModule
import studio.forface.freshtv.commonandroid.commonAndroidModule
import studio.forface.freshtv.domain.useCasesModule
import studio.forface.freshtv.localdata.localDataModule
import studio.forface.freshtv.parsers.parsersModule
import studio.forface.freshtv.settings.settingsModule

/** A [List] of [Module] all the business modules */
val businessModules =
        androidDatabaseModule +
        commonAndroidModule +
        localDataModule +
        parsersModule +
        settingsModule +
        useCasesModule