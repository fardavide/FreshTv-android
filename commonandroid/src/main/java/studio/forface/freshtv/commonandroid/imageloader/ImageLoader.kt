package studio.forface.freshtv.commonandroid.imageloader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import java.io.File

/**
 * @author Davide Giuseppe Farella.
 * This interface represents an abstraction layout for handle Image loading.
 */
interface ImageLoader {

    companion object {
        val defaultShape = Shape.SQUARE
    }

    /**
     * A custom shape for the image to load.
     */
    enum class Shape { ROUND, SQUARE }

    /**
     * Load image from [bitmap] into an [ImageView].
     * @param bitmap the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [Shape.SQUARE] will be used as default.
     */
    fun bitmapToImageView( bitmap: Bitmap, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [drawable] into an [ImageView].
     * @param drawable the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [Shape.SQUARE] will be used as default.
     */
    fun drawableToImageView( drawable: Drawable, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [file] into an [ImageView].
     * @param file the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [Shape.SQUARE] will be used as default.
     */
    fun fileToImageView( file: File, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [resourceId] into an [ImageView].
     * @param resourceId the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [Shape.SQUARE] will be used as default.
     */
    fun resourceToImageView( resourceId: Int, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [uri] into an [ImageView].
     * @param uri the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [Shape.SQUARE] will be used as default.
     */
    fun uriToImageView( uri: Uri, target: ImageView, shape: Shape = defaultShape )

    /**
     * Load image from [url] into an [ImageView].
     * @param url the source of the image
     * @param target the [ImageView] that will show the image.
     * @param shape is an optional [Shape] for the image to load, if no parameter is passed,
     * [Shape.SQUARE] will be used as default.
     */
    fun urlToImageView( url: String, target: ImageView, shape: Shape = defaultShape )
}