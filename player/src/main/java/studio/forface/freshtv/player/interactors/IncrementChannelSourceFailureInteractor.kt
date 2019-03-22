package studio.forface.freshtv.player.interactors

import studio.forface.freshtv.domain.usecases.IncrementChannelMediaFailure

/**
 * An interactor for increment the `Source` failure for a `Channel`
 *
 * @author Davide Giuseppe Farella.
 */
internal class IncrementChannelSourceFailureInteractor(
        private val incrementFailure: IncrementChannelMediaFailure
) {

    /** Increment the failure for the given [sourceUrl] for the `Channel` with the given [channelId] */
    operator fun invoke( channelId: String, sourceUrl: String ) =
            incrementFailure( channelId, sourceUrl )
}
