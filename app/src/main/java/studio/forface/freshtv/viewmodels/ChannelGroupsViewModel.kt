@file:Suppress("LeakingThis")

package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.interactors.SaveLastGroupInteractor
import studio.forface.freshtv.interactors.SaveLastMovieGroupInteractor
import studio.forface.freshtv.interactors.SaveLastTvGroupInteractor
import studio.forface.freshtv.presenters.ChannelGroupsPresenter
import studio.forface.freshtv.presenters.MovieChannelGroupsPresenter
import studio.forface.freshtv.presenters.TvChannelGroupsPresenter
import studio.forface.freshtv.uimodels.ChannelGroupsUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * A [ViewModel] that get `TvChannel`s Groups
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class ChannelGroupsViewModel(
    private val presenter: ChannelGroupsPresenter,
    private val lastGroupInteractor: SaveLastGroupInteractor
): ScopedViewModel( IO ) {

    /** A [LockedViewStateStore] of [ChannelGroupsUiModel] */
    val groups = ViewStateStore<ChannelGroupsUiModel>().lock

    init {
        groups.setLoading()
        launch {
            for ( uiModel in presenter() )
                groups.postData( uiModel )
        }
    }

    /** Save the given [groupName] as last group name */
    fun saveLastGroupName( groupName: String? ) {
        lastGroupInteractor.saveLastGroupName( groupName )
    }
}

/**
 * A [ViewModel] that get `MovieChannel`s Groups
 * Inherit from [ChannelGroupsViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class MovieChannelGroupsViewModel(
    presenter: MovieChannelGroupsPresenter,
    lastGroupInteractor: SaveLastMovieGroupInteractor
): ChannelGroupsViewModel( presenter, lastGroupInteractor )

/**
 * A [ViewModel] that get `TvChannel`s Groups
 * Inherit from [ChannelGroupsViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvChannelGroupsViewModel(
    presenter: TvChannelGroupsPresenter,
    lastGroupInteractor: SaveLastTvGroupInteractor
): ChannelGroupsViewModel( presenter, lastGroupInteractor )