package studio.forface.freshtv.uimodels

/**
 * @author Davide Giuseppe Farella
 * An Ui Model representing a Tv Channel with its Tv Guide ( if available ).
 */
internal data class TvChannelUiModel(
        val id: String,
        val name: String,
        val imageUrl: String,
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