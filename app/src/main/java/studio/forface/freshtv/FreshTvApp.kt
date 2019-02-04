package studio.forface.freshtv

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import studio.forface.freshtv.dimodules.businessModules

/**
 * @author Davide Giuseppe Farella.
 * The [Application]
 */
class FreshTvApp: Application() {

    /** When the app is created */
    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@FreshTvApp )
            // declare modules
            modules(businessModules + appModule )
        }
    }
}