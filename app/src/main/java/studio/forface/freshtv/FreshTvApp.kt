package studio.forface.freshtv

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.crashlytics.android.Crashlytics
import com.jakewharton.threetenabp.AndroidThreeTen
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import studio.forface.freshtv.dimodules.otherErrorGenerators
import studio.forface.freshtv.dimodules.otherModules
import studio.forface.freshtv.dimodules.plus
import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.domain.gateways.SettingsListener
import studio.forface.freshtv.domain.utils.days
import studio.forface.freshtv.domain.utils.hours
import studio.forface.freshtv.services.DeleteOldGuidesWorker
import studio.forface.freshtv.services.RefreshChannelsWorker
import studio.forface.freshtv.services.RefreshTvGuidesWorker
import studio.forface.theia.TheiaConfig
import studio.forface.theia.cache.weeks
import studio.forface.theia.invoke
import studio.forface.viewstatestore.ViewStateStoreConfig
import studio.forface.viewstatestore.invoke
import studio.forface.viewstatestore.plus
import timber.log.Timber

/**
 * The [Application]
 *
 * @author Davide Giuseppe Farella
 */
@Suppress("unused")
class FreshTvApp: Application() {

    /** A reference to [AppSettings] */
    private val settings by inject<AppSettings>()

    /** A reference to the active [SettingsListener]s */
    private val settingsListeners = mutableListOf<SettingsListener>()

    /** When the app is created */
    override fun onCreate() {
        super.onCreate()

        // Init Crashlytics.
        Fabric.with(this, Crashlytics() )

        // Init ThreeTen Android backport.
        AndroidThreeTen.init(this )

        // Start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@FreshTvApp )
            // declare modules
            modules(appModule + otherModules() )
        }

        // Init Timber
        Timber.plant( get() )

        // Set night mode
        setDefaultNightMode( settings.nightMode )
        settingsListeners += settings.addListener( AppSettings::nightMode, ::setDefaultNightMode )

        // Configure Theia
        TheiaConfig {
            cacheDuration = 2.weeks
            loggingEnabled = BuildConfig.DEBUG
            logger = get()
        }

        // Configure ViewStateStore
        ViewStateStoreConfig {
            dropOnSame = true
            errorStateGenerator = appErrorStateGenerator + otherErrorGenerators()
        }

        // Enqueue Works
        RefreshChannelsWorker.enqueue( 1.days )
        RefreshTvGuidesWorker.enqueue( 3.hours )
        DeleteOldGuidesWorker.enqueue( 12.hours )
    }

    /** When the app is terminated */
    override fun onTerminate() {
        settingsListeners.forEach( settings::removeListener )
        super.onTerminate()
    }

    /** Enable or disable night mode regarding [enabled] param */
    private fun setDefaultNightMode( enabled: Boolean ) {
        AppCompatDelegate.setDefaultNightMode(
            if ( enabled ) MODE_NIGHT_YES else MODE_NIGHT_NO
        )
    }
}