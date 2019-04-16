package studio.forface.freshtv.uimodels

import androidx.annotation.DrawableRes

/**
 * An Ui Model representing a Tv Channel with its Tv Guide ( if available ).
 *
 * @author Davide Giuseppe Farella
 */
internal data class TvChannelUiModel(
        val id: String,
        val name: String,
        val image: String?,
        @DrawableRes val imagePlaceHolder: Int,
        val favorite: Boolean,
        @DrawableRes val favoriteImage: Int,
        val favoriteImageNeedTint: Boolean,
        val currentProgram: CurrentProgram?
) {
    /** A data class containing info of the current Program of `TvChannel` */
    data class CurrentProgram(
            val title: String,
            val startTime: String,
            val endTime: String,
            val progressPercentage: Int
    )
}