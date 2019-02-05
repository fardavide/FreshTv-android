package studio.forface.freshtv.commonandroid

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.freshtv.commonandroid.imageloader.ImageLoader
import studio.forface.freshtv.commonandroid.imageloader.PicassoImageLoader

/** A [Module] that handles dependencies of `commonandroid` module */
val commonAndroidModule = module {
    single<ImageLoader> { PicassoImageLoader( androidContext() ) }
    single { Toast( androidContext() ) }
}