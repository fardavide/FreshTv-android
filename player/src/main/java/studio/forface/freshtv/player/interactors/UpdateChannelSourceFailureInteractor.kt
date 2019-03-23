package studio.forface.freshtv.player.interactors

import studio.forface.freshtv.domain.usecases.IncrementChannelMediaFailure
import studio.forface.freshtv.domain.usecases.ResetChannelMediaFailure

/**
 * An interactor for increment the `Source` failure for a `Channel`
 *
 * @author Davide Giuseppe Farella.
 */
internal class UpdateChannelSourceFailureInteractor(
        private val incrementFailure: IncrementChannelMediaFailure,
        private val resetFailure: ResetChannelMediaFailure
) {

    /** Increment the failure for the given [sourceUrl] for the `Channel` with the given [channelId] */
    fun increment( channelId: String, sourceUrl: String ) =
            incrementFailure( channelId, sourceUrl )

    /** Reset the failure for the given [sourceUrl] for the `Channel` with the given [channelId] */
    fun reset( channelId: String, sourceUrl: String ) =
            resetFailure( channelId, sourceUrl )
}
