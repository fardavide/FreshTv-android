package studio.forface.freshtv.player.uiModels

import androidx.annotation.DrawableRes
import studio.forface.freshtv.domain.entities.IChannel

/**
 * An Ui Model representing a `Channel`
 *
 * @author Davide Giuseppe Farella
 */
internal data class ChannelUiModel(
        val id: String,
        val title: String,
        val image: String?,
        val favorite: Boolean,
        @DrawableRes val favoriteImage: Int,
        val type: IChannel.Type
) {

    enum class Type { MOVIE, TV }
}