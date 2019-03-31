package studio.forface.freshtv.uimodels

import androidx.annotation.DrawableRes

/**
 * An Ui Model representing a Movie Channel
 *
 * @author Davide Giuseppe Farella
 */
internal data class MovieChannelUiModel(
        val id: String,
        val name: String,
        val description: String?,
        val image: String?,
        @DrawableRes val imagePlaceHolder: Int,
        val favorite: Boolean,
        @DrawableRes val favoriteImage: Int
)