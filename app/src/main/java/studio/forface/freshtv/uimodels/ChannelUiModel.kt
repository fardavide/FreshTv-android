package studio.forface.freshtv.uimodels

import androidx.annotation.DrawableRes

/**
 * A common interface for Ui Model representing a Channel
 *
 * @author Davide Giuseppe Farella
 */
internal interface ChannelUiModel {
    val id: String
    val name: String
    val image: String?
    @get: DrawableRes val imagePlaceHolder: Int
    val favorite: Boolean
    @get: DrawableRes val favoriteImage: Int
    val favoriteImageNeedTint: Boolean
}

/**
 * An Ui Model representing a Movie Channel
 * Inherit from [ChannelUiModel]
 *
 * @author Davide Giuseppe Farella
 */
internal data class MovieChannelUiModel(
    override val id: String,
    override val name: String,
    val description: String?,
    override val image: String?,
    @DrawableRes override val imagePlaceHolder: Int,
    override val favorite: Boolean,
    @DrawableRes override val favoriteImage: Int,
    override val favoriteImageNeedTint: Boolean
) : ChannelUiModel

/**
 * An Ui Model representing a Tv Channel with its Tv Guide ( if available ).
 * Inherit from [ChannelUiModel]
 *
 * @author Davide Giuseppe Farella
 */
internal data class TvChannelUiModel(
    override val id: String,
    override val name: String,
    override val image: String?,
    @DrawableRes override val imagePlaceHolder: Int,
    override val favorite: Boolean,
    @DrawableRes override val favoriteImage: Int,
    override val favoriteImageNeedTint: Boolean,
    val currentProgram: CurrentProgram?
) : ChannelUiModel {

    /** A data class containing info of the current Program of `TvChannel` */
    data class CurrentProgram(
        val title: String,
        val startTime: String,
        val endTime: String,
        val progressPercentage: Int
    )
}