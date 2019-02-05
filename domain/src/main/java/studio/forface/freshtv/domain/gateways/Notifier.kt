package studio.forface.freshtv.domain.gateways

/**
 * @author Davide Giuseppe Farella.
 * A Service for notify an event to the user.
 */
interface Notifier {

    /** Show an error event */
    fun error( message: String )

    /** Show an error event */
    fun error( t: Throwable )

    /** Show an info event */
    fun info( message: String )

    /** Show an info event */
    fun info( t: Throwable )

    /** Show a warn event */
    fun warn( message: String )
}