package studio.forface.freshtv.mappers

import studio.forface.freshtv.commonandroid.mappers.UiModelMapper
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.Unsupported
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.unsupported
import studio.forface.freshtv.uimodels.ChannelGroupUiModel
import studio.forface.freshtv.uimodels.ChannelGroupsUiModel
import studio.forface.freshtv.R.drawable.ic_tv as tvDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite as favoriteDrawable
import studio.forface.freshtv.commonandroid.R.drawable.ic_favorite_black as notFavoriteDrawable

/**
 * A Mapper of [ChannelGroupUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella.
 */
internal class ChannelGroupUiModelMapper : UiModelMapper<ChannelGroup, ChannelGroupUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun ChannelGroup.toUiModel() = ChannelGroupUiModel( name )

    /** @see UiModelMapper.toEntity */
    override fun ChannelGroupUiModel.toEntity() = unsupported
}

/**
 * A Mapper of [ChannelGroupsUiModel]
 * Inherit from [UiModelMapper]
 *
 * @author Davide Giuseppe Farella.
 */
internal class ChannelGroupsUiModelMapper(
    private val subMapper: ChannelGroupUiModelMapper
): UiModelMapper<GroupsWithLastChannelName, ChannelGroupsUiModel, Unsupported>() {

    /** @see UiModelMapper.toUiModel */
    override fun GroupsWithLastChannelName.toUiModel(): ChannelGroupsUiModel {
        val groupsUiModel: List<ChannelGroupUiModel> = groups.map( subMapper ) { it.toUiModel() }
        // Find the index ( position ) for lastGroupName, if not null; if the result is less than 0 ( item not found ),
        // set it as null
        val savedPosition = lastGroupName?.let { name -> groups.indexOfFirst { it.name == name } }
            ?.let { if ( it > 0 ) it else null }

        return ChannelGroupsUiModel( groupsUiModel, savedPosition )
    }

    /** @see UiModelMapper.toEntity */
    override fun ChannelGroupsUiModel.toEntity() = unsupported
}

/**
 * A typealias for a [Pair] of a [List] of [ChannelGroup] and an OPTIONAL [String] representing the name of the last
 * [ChannelGroup]
 */
private typealias GroupsWithLastChannelName = Pair<List<ChannelGroup>, String?>

/** @return a [List] of [ChannelGroup] from [GroupsWithLastChannelName] */
private val GroupsWithLastChannelName.groups get() = first

/** @return an OPTIONAL [String] representing the name of the last [ChannelGroup] from [GroupsWithLastChannelName] */
private val GroupsWithLastChannelName.lastGroupName get() = second