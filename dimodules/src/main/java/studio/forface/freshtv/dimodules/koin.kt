package studio.forface.freshtv.dimodules

import org.koin.core.module.Module
import studio.forface.freshtv.androiddatabase.androidDatabaseModule
import studio.forface.freshtv.androiddatabase.sqldelight.sqlDelightAndroidDatabaseModule
import studio.forface.freshtv.commonandroid.commonAndroidModule
import studio.forface.freshtv.domain.useCasesModule
import studio.forface.freshtv.localdata.sqldelight.sqlDelightLocalDataModule
import studio.forface.freshtv.parsers.parsersModule
import studio.forface.freshtv.player.playerModule
import studio.forface.freshtv.preferences.preferencesModule
import studio.forface.freshtv.settings.settingsModule

/** A [List] of [Module] all the business modules */
fun otherModules( database: Database ) =
        androidDatabaseModule +
        commonAndroidModule +
        parsersModule +
        playerModule +
        preferencesModule +
        settingsModule +
        useCasesModule +
        //when ( database ) {
        //        Database.SqlDelight ->
        sqlDelightLocalDataModule + sqlDelightAndroidDatabaseModule
        //        Database.Room -> roomLocalDataModule + roomAndroidDatabaseModule
        //}

enum class Database { SqlDelight, Room }