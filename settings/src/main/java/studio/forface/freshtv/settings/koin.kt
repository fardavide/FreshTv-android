package studio.forface.freshtv.settings

import com.russhwolf.settings.PlatformSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.domain.gateways.AppSettings

/** A [Module] that handles dependencies for use cases of `settings` module */
val settingsModule = module {
    single { PlatformSettings.Factory( androidContext() ).create() }

    factory<AppSettings> { AndroidAppSettings( settings = get() ) }
}