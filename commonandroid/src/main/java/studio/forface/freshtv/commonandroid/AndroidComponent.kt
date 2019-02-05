package studio.forface.freshtv.commonandroid

import org.koin.core.KoinComponent
import org.koin.core.get
import studio.forface.freshtv.commonandroid.imageloader.ImageLoader

/**
 * @author Davide Giuseppe Farella
 * A [KoinComponent] that provides common dependencies ( utilities ) to the Android components such as Activities or
 * Fragments
 */
interface AndroidComponent : KoinComponent {

    /** A reference to [ImageLoader] for load images */
    val imageLoader get() = get<ImageLoader>()

    /** A reference to [Toast] for show Toast messages */
    val toast get() = get<Toast>()
}