package studio.forface.freshtv.dimodules

import org.koin.core.module.Module
import studio.forface.freshtv.about.aboutModule
import studio.forface.freshtv.androiddatabase.androidDatabaseModule
import studio.forface.freshtv.androiddatabase.sqldelight.sqlDelightAndroidDatabaseModule
import studio.forface.freshtv.commonandroid.commonAndroidModule
import studio.forface.freshtv.domain.useCasesModule
import studio.forface.freshtv.localdata.sqldelight.sqlDelightLocalDataModule
import studio.forface.freshtv.parsers.parsersModule
import studio.forface.freshtv.player.playerErrorStateGenerator
import studio.forface.freshtv.player.playerModule
import studio.forface.freshtv.preferences.preferencesModule
import studio.forface.freshtv.settings.settingsModule
import studio.forface.viewstatestore.ErrorStateGenerator

/** A [List] of [Module]s for the app */
fun otherModules() =
        androidDatabaseModule +
        aboutModule +
        commonAndroidModule +
        parsersModule +
        playerModule +
        preferencesModule +
        settingsModule +
        sqlDelightLocalDataModule + sqlDelightAndroidDatabaseModule +
        useCasesModule

/** A [List] of [ErrorStateGenerator]s for the app */
fun otherErrorGenerators(): ErrorStateGenerator =
        playerErrorStateGenerator