package studio.forface.freshtv.player.uiModels

import androidx.annotation.DrawableRes

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
        val type: Type
) {

    enum class Type { MOVIE, TV }
}