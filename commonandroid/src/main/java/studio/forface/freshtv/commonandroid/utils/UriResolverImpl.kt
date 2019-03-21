package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.content.ContentResolver
import androidx.core.net.toUri
import studio.forface.freshtv.domain.gateways.UriResolver
import java.io.InputStream

/** Implementation of [UriResolver] */
class UriResolverImpl( private val context: Context ): UriResolver {

    /** @return an [InputStream] using [ContentResolver.openInputStream] */
    override fun invoke( stringUri: String ): InputStream =
            context.contentResolver.openInputStream( stringUri.toUri() )!!
}