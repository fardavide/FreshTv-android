package studio.forface.freshtv.commonandroid

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.commonandroid.frameworkcomponents.Toast
import studio.forface.freshtv.commonandroid.imageloader.ImageLoader
import studio.forface.freshtv.commonandroid.imageloader.PicassoImageLoader
import studio.forface.freshtv.commonandroid.notifier.AndroidNotifier
import studio.forface.freshtv.commonandroid.notifier.TimberTree
import studio.forface.freshtv.domain.gateways.Notifier
import timber.log.Timber

/** A [Module] that handles dependencies of `commonandroid` module */
val commonAndroidModule = module {
    single<ImageLoader> { PicassoImageLoader( androidContext() ) }
    single<Notifier> { AndroidNotifier( toast = get() ) }
    factory<Timber.Tree> { TimberTree() }
    factory { Toast( androidContext() ) }
}