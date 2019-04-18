@file:Suppress("EXPERIMENTAL_FEATURE_WARNING") // inline class

package studio.forface.freshtv.uimodels

/**
 * An Ui Model representing a Channel's Group
 *
 * @author Davide Giuseppe Farella
 */
internal inline class ChannelGroupUiModel( val name: String )

/**
 * An Ui Model representing a list of Channel's Group and a saved position
 *
 * @param groups a [List] of [ChannelGroupUiModel]
 *
 * @param savedPosition an OPTIONAL [Int] representing the position calculated on base of the name of the last group
 * selected in the UI
 *
 *
 * @author Davide Giuseppe Farella
 */
internal data class ChannelGroupsUiModel( val groups: List<ChannelGroupUiModel>, val savedPosition: Int? )