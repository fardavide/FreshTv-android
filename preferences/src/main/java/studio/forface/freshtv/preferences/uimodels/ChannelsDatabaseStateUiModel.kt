package studio.forface.freshtv.preferences.uimodels

/**
 * An Ui Model representing the empty state of database for `Channel`s and `Group`s
 *
 * @author Davide Giuseppe Farella
 */
internal enum class ChannelsDatabaseStateUiModel {

    /** Database has NOT `Channel`s nor `Group`s */
    Empty,

    /** Database has SOME `Channel`s or `Group`s */
    NotEmpty
}