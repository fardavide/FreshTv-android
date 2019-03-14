package studio.forface.freshtv.commonandroid.log

import studio.forface.theia.TheiaException
import studio.forface.theia.actualMessage
import studio.forface.theia.log.TheiaLogger
import timber.log.Timber

/**
 * Implementation of [TheiaLogger]
 *
 * @author Davide Giuseppe Farella.
 */
class TheiaLibLogger: TheiaLogger {

    /** @see TheiaLogger.error */
    override fun error( ex: TheiaException ) {
        if ( logEnabled ) Timber.e( ex )
        else Timber.i( ex )
    }

    /** @see TheiaLogger.info */
    override fun info( ex: TheiaException ) {
        if ( logEnabled ) Timber.i( ex.actualMessage )
    }
}