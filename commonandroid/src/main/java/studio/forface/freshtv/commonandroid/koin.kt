package studio.forface.freshtv.commonandroid

import android.content.res.Resources
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.commonandroid.notifier.Toast
import studio.forface.freshtv.commonandroid.imageloader.ImageLoader
import studio.forface.freshtv.commonandroid.imageloader.PicassoImageLoader
import studio.forface.freshtv.commonandroid.notifier.AndroidNotifier
import studio.forface.freshtv.commonandroid.notifier.TimberTree
import timber.log.Timber

/** A [Module] that handles dependencies of `commonandroid` module */
val commonAndroidModule = module {
    factory<Resources> { androidContext().resources }

    single<ImageLoader> { PicassoImageLoader( androidContext() ) }
    single { AndroidNotifier( resources = get(), toast = get() ) }
    factory<Timber.Tree> { TimberTree() }
    factory { Toast( androidContext() ) }
}