package studio.forface.freshtv.commonandroid

import android.content.res.Resources
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.commonandroid.log.TheiaLibLogger
import studio.forface.freshtv.commonandroid.log.TimberTree
import studio.forface.freshtv.commonandroid.notifier.AndroidNotifier
import studio.forface.freshtv.commonandroid.notifier.Toast
import studio.forface.freshtv.commonandroid.utils.UriResolverImpl
import studio.forface.freshtv.domain.gateways.UriResolver
import studio.forface.theia.log.TheiaLogger
import timber.log.Timber

/** A [Module] that handles dependencies of `commonandroid` module */
val commonAndroidModule = module {
    factory<Resources> { androidContext().resources }

    single { AndroidNotifier( resources = get(), toast = get() ) }
    factory<TheiaLogger> { TheiaLibLogger() }
    factory<Timber.Tree> { TimberTree() }
    factory { Toast( androidContext() ) }
    factory<UriResolver> { UriResolverImpl( androidContext() ) }
}