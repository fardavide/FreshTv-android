package studio.forface.freshtv.settings

import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.settings.helper.Settings

/** A [Module] that handles dependencies for use cases of `settings` module */
val settingsModule = module {
    factory<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences( androidContext() ) }
    single { Settings(preferences = get()) }

    factory<AppSettings> { AndroidAppSettings( settings = get() ) }
}