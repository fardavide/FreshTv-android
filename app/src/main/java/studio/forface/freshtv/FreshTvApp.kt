package studio.forface.freshtv

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import studio.forface.freshtv.dimodules.Database
import studio.forface.freshtv.dimodules.otherModules
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
import timber.log.Timber

/**
 * @author Davide Giuseppe Farella.
 * The [Application]
 */
@Suppress("unused")
class FreshTvApp: Application() {

    /** When the app is created */
    override fun onCreate() {
        super.onCreate()

        // Init Crashlytics.
        // Fabric.with(this, Crashlytics() ) TODO register on Firebase

        // Init ThreeTen Android backport.
        AndroidThreeTen.init( this )

        // Start Koin!
        startKoin {
            // declare used Android context
            androidContext( this@FreshTvApp )
            // declare modules
            modules( otherModules( Database.SqlDelight ) + appModule )
        }

        // Init Timber
        Timber.plant( get() )

        // Configure Theia
        TheiaConfig {
            cacheDuration = 2.weeks
            loggingEnabled = BuildConfig.DEBUG
            logger = get()
        }

        // Configure ViewStateStore
        ViewStateStoreConfig {
            dropOnSame = true
            errorStateGenerator = { default }
        }

        // Enqueue Works
        RefreshChannelsWorker.enqueue( 1.days )
        RefreshTvGuidesWorker.enqueue( 3.days )
        DeleteOldGuidesWorker.enqueue( 5.days )
    }
}