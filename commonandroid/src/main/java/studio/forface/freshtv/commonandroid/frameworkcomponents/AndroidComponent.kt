package studio.forface.freshtv.commonandroid.frameworkcomponents

import org.koin.core.KoinComponent
import org.koin.core.get
import studio.forface.freshtv.commonandroid.imageloader.ImageLoader
import studio.forface.freshtv.commonandroid.notifier.AndroidNotifier
import studio.forface.freshtv.domain.gateways.Notifier

/**
 * @author Davide Giuseppe Farella
 * A [KoinComponent] that provides common dependencies ( utilities ) to the Android components such as Activities or
 * Services
 */
interface AndroidComponent : KoinComponent {

    /** A reference to [Notifier] for show messages */
    val notifier get() = get<AndroidNotifier>()
}

/** An [AndroidComponent] that has an UI */
interface AndroidUiComponent : AndroidComponent {

    /** A reference to [ImageLoader] for load images */
    val imageLoader get() = get<ImageLoader>()
}