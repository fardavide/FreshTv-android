package studio.forface.freshtv.uimodels

/**
 * @author Davide Giuseppe Farella
 * An Ui Model representing the availability of Channels
 */
internal data class ChannelsAvailabilityUiModel(
    val hasMovies: Boolean,
    val hasTvs: Boolean
) {
    /** @return `true` if at least one of [hasMovies] and [hasTvs] is true */
    val hasAny get() = hasMovies || hasTvs
}

/** @return `true` if [ChannelsAvailabilityUiModel] is not null and [ChannelsAvailabilityUiModel.hasAny] is true */
internal val ChannelsAvailabilityUiModel?.hasAny get() = this?.hasAny == true

/** @return `true` if [ChannelsAvailabilityUiModel] is not null and [ChannelsAvailabilityUiModel.hasMovies] is true */
internal val ChannelsAvailabilityUiModel?.hasMovies get() = this?.hasMovies == true

/** @return `true` if [ChannelsAvailabilityUiModel] is not null or [ChannelsAvailabilityUiModel.hasAny] is false */
internal val ChannelsAvailabilityUiModel?.hasNothing get() = this?.hasAny != true

/** @return `true` if [ChannelsAvailabilityUiModel] is not null and [ChannelsAvailabilityUiModel.hasTvs] is true */
internal val ChannelsAvailabilityUiModel?.hasTvs get() = this?.hasTvs == true