package studio.forface.freshtv.commonandroid.imageloader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import java.io.File
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction3

/**
 * @author Davide Giuseppe Farella.
 * This interface represents an abstraction layout for handle Image loading.
 */
interface ImageLoader {

    companion object {
        /** The default [Shape] for [ImageLoader] */
        val defaultShape = Shape.SQUARE
    }

    /**
     * A custom shape for the image to load.
     */
    enum class Shape { ROUND, SQUARE }

    /**
     * Load image from [Any] source into an [ImageView].
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [defaultShape] will be used as default.
     */
    fun anyToImageView( image: Any, target: ImageView, shape: Shape = defaultShape ) {
        @Suppress("UNCHECKED_CAST") val function = when( image ) {
            is Bitmap -> ::bitmapToImageView
            is Drawable -> ::drawableToImageView
            is File -> ::fileToImageView
            is Int -> ::resourceToImageView
            is Uri -> ::uriToImageView
            is String -> ::urlToImageView
            else -> throw NotImplementedError( "${image::class.qualifiedName} not implemented" )
        } as LoadFunction
        function( image, target, shape )
    }

    /**
     * Load image from [bitmap] into an [ImageView].
     * @param bitmap the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [defaultShape] will be used as default.
     */
    fun bitmapToImageView( bitmap: Bitmap, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [drawable] into an [ImageView].
     * @param drawable the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [defaultShape] will be used as default.
     */
    fun drawableToImageView( drawable: Drawable, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [file] into an [ImageView].
     * @param file the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [defaultShape] will be used as default.
     */
    fun fileToImageView( file: File, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [resourceId] into an [ImageView].
     * @param resourceId the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [defaultShape] will be used as default.
     */
    fun resourceToImageView( resourceId: Int, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [uri] into an [ImageView].
     * @param uri the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [defaultShape] will be used as default.
     */
    fun uriToImageView( uri: Uri, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [url] into an [ImageView].
     * @param url the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [defaultShape] will be used as default.
     */
    fun urlToImageView( url: String, target: ImageView, shape: Shape = defaultShape )


    /** A builder for load images with DSL */
    @ImageLoaderDsl
    class Builder @PublishedApi internal constructor (
        private val load: (image: Any, target: ImageView, shape: Shape) -> Unit
    ) {
        lateinit var image: Any
        lateinit var target: ImageView
        var shape = defaultShape

        /** Call [load] abstraction of [anyToImageView] */
        fun build() {
            load( image, target, shape )
        }
    }
}

/** Use [ImageLoader] with DSL with [ImageLoader.Builder] as lambda receiver of [builder] */
inline operator fun ImageLoader.invoke( builder: ImageLoader.Builder.() -> Unit ) {
    ImageLoader.Builder { image, target, shape -> anyToImageView( image, target, shape ) }.apply {
        builder()
        build()
    }
}

/** [DslMarker] for [ImageLoader] dsl */
@DslMarker annotation class ImageLoaderDsl

/** A typealias for a [KFunction] of [ImageLoader] that loads an image into an [ImageView] */
private typealias LoadFunction = KFunction3<
        @ParameterName(name = "image") Any,
        @ParameterName(name = "target") ImageView,
        @ParameterName(name = "shape") ImageLoader.Shape,
        Unit>