package studio.forface.freshtv

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.jakewharton.threetenabp.AndroidThreeTen
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import studio.forface.freshtv.commonandroid.notifier.TimberTree
import studio.forface.freshtv.dimodules.businessModules
import studio.forface.freshtv.domain.utils.days
import studio.forface.freshtv.services.DeleteOldGuidesWorker
import timber.log.Timber

/**
 * @author Davide Giuseppe Farella.
 * The [Application]
 */
class FreshTvApp: Application() {

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
            modules(businessModules + appModule )
        }

        // Init Timber
        Timber.plant( get<TimberTree>() )

        // Enqueue Works
        DeleteOldGuidesWorker.enqueue( 3.days, 1.days )
    }
}