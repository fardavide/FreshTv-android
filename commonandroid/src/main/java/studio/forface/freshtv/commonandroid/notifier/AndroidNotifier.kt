package studio.forface.freshtv.commonandroid.notifier

import studio.forface.freshtv.commonandroid.frameworkcomponents.Toast
import studio.forface.freshtv.domain.gateways.Notifier
import studio.forface.freshtv.domain.utils.nonNullMessage
import timber.log.Timber

/**
 * @author Davide Giuseppe Farella.
 * Android implementation of [Notifier].
 */
class AndroidNotifier( private val toast: Toast): Notifier {

    /** @see Notifier.error */
    override fun error(message: String) {
        Timber.e( message )
        toast.error( message )
    }

    /** @see Notifier.error */
    override fun error( t: Throwable ) {
        Timber.e( t )
        toast.error( t.nonNullMessage )
    }

    /** @see Notifier.info */
    override fun info( message: String ) {
        Timber.i( message )
        toast.info( message )
    }

    /** @see Notifier.info */
    override fun info( t: Throwable ) {
        Timber.i( t )
        toast.info( t.nonNullMessage )
    }

    /** @see Notifier.warn */
    override fun warn( message: String ) {
        Timber.w( message )
        toast.warn( message )
    }
}