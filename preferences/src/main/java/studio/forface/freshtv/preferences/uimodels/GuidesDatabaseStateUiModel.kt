package studio.forface.freshtv.preferences.uimodels

/**
 * An Ui Model representing the empty state of database for `Guide`s
 *
 * @author Davide Giuseppe Farella
 */
internal enum class GuidesDatabaseStateUiModel {

    /** Database has NOT `Guide`s */
    Empty,

    /** Database has SOME `Guide`s */
    NotEmpty
}