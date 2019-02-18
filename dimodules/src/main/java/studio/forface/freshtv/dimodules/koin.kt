package studio.forface.freshtv.dimodules

import org.koin.core.module.Module
import studio.forface.freshtv.androiddatabase.androidDatabaseModule
import studio.forface.freshtv.commonandroid.commonAndroidModule
import studio.forface.freshtv.domain.useCasesModule
import studio.forface.freshtv.localdata.room.roomLocalDataModule
import studio.forface.freshtv.localdata.sqldelight.sqlDelightlocalDataModule
import studio.forface.freshtv.parsers.parsersModule
import studio.forface.freshtv.settings.settingsModule

/** A [List] of [Module] all the business modules */
fun otherModules( database: Database ) =
        androidDatabaseModule +
        commonAndroidModule +
        parsersModule +
        settingsModule +
        useCasesModule +
        when ( database ) {
                Database.SqlDelight -> sqlDelightlocalDataModule
                Database.Room -> roomLocalDataModule
        }

enum class Database { SqlDelight, Room }