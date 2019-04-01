package studio.forface.freshtv.player.presenters

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.usecases.GetChannel

/**
 * A Presenter for get a `Channel`s Type
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelTypePresenter( private val getChannel: GetChannel ) {

    /** @return [IChannel.Type] for the given [id] */
    operator fun invoke( id: String ) = getChannel.invoke( id ).type
}