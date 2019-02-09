package studio.forface.freshtv.commonandroid.imageloader

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation
import studio.forface.freshtv.commonandroid.BuildConfig
import java.io.File

/**
 * @author Davide Giuseppe Farella.
 * Implementation of [ImageLoader] for provide [Picasso] features.
 */
internal class PicassoImageLoader( context: Context) : ImageLoader {
    /**
     * GET a reference to [Picasso].
     */
    private val picasso get() = Picasso.get()

    init {
        val instance = Picasso.Builder( context )
                .loggingEnabled( BuildConfig.DEBUG )
                .build()
        Picasso.setSingletonInstance( instance )
    }

    /**
     * @see ImageLoader.bitmapToImageView
     * @see Bitmap.applyShape
     */
    override fun bitmapToImageView( bitmap: Bitmap, target: ImageView, shape: ImageLoader.Shape ) {
        target.setImageBitmap( bitmap.applyShape( shape ) )
    }

    /**
     * @see ImageLoader.drawableToImageView
     * @see Drawable.toBitmap
     * @see Bitmap.applyShape
     */
    override fun drawableToImageView( drawable: Drawable, target: ImageView, shape: ImageLoader.Shape ) {
        target.setImageBitmap( drawable.toBitmap().applyShape( shape ) )
    }

    /**
     * @see ImageLoader.fileToImageView
     * @see Drawable.toBitmap
     * @see Bitmap.applyShape
     */
    override fun fileToImageView( file: File, target: ImageView, shape: ImageLoader.Shape ) {
        picasso.load( file ).applyShape( shape ).into( target )
    }

    /**
     * @see ImageLoader.resourceToImageView
     * @see RequestCreator.applyShape
     */
    override fun resourceToImageView( resourceId: Int, target: ImageView, shape: ImageLoader.Shape ) {
        picasso.load( resourceId ).applyShape( shape ).into( target )
    }

    /**
     * @see ImageLoader.uriToImageView
     * @see RequestCreator.applyShape
     */
    override fun uriToImageView( uri: Uri, target: ImageView, shape: ImageLoader.Shape ) {
        picasso.load( uri ).applyShape( shape ).into( target )
    }

    /**
     * @see ImageLoader.urlToImageView
     * @see RequestCreator.applyShape
     */
    override fun urlToImageView( url: String, target: ImageView, shape: ImageLoader.Shape ) {
        picasso.load( url ).applyShape( shape ).into( target )
    }


    /**
     * Apply the [ImageLoader.Shape] to the [Bitmap].
     */
    private fun Bitmap.applyShape( shape: ImageLoader.Shape ) =
            if ( shape == ImageLoader.Shape.ROUND )
                CircleTransform().transform(this )
            else this

    /**
     * Apply the [ImageLoader.Shape] to the [RequestCreator].
     */
    private fun RequestCreator.applyShape( shape: ImageLoader.Shape ) =
            if ( shape == ImageLoader.Shape.ROUND )
                this.transform( CircleTransform() )
            else this
}

/**
 * @author Davide Giuseppe Farella.
 * Originally created by julian on 13/6/21.
 * A Circle [Transformation] for [Picasso].
 */
internal class CircleTransform : Transformation {

    private var x: Int = 0
    private var y: Int = 0

    override fun transform( source: Bitmap ): Bitmap {
        val size = Math.min( source.width, source.height )

        x = ( source.width - size ) / 2
        y = ( source.height - size ) / 2

        val squaredBitmap = Bitmap.createBitmap( source, x, y, size, size )
        if ( squaredBitmap != source ) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap( size, size, source.config )

        val canvas = Canvas( bitmap )
        val paint = Paint()
        val shader = BitmapShader( squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP )
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle( r, r, r, paint )

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String = "circle(x=$x,y=$y)"
}