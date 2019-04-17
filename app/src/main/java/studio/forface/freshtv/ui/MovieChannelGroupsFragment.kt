package studio.forface.freshtv.ui

import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.ui.ParentFragment
import studio.forface.freshtv.viewmodels.MovieChannelGroupsViewModel

/**
 * A `Fragment` for see the stored `MovieChannel`s Groups
 * Inherit from [AbsChannelGroupsFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class MovieChannelGroupsFragment :
    AbsChannelGroupsFragment( { groupName -> MovieChannelsFragment( groupName ) } ) {

    companion object {
        /** @return `NavDirections` to this `Fragment` */
        fun directions() = HomeFragmentDirections.actionToMovieChannelGroupsFragment()
    }

    /** A reference to [MovieChannelGroupsViewModel] for retrieve the stored `Channel`s */
    private val channelGroupsViewModel by viewModel<MovieChannelGroupsViewModel>()

    /** An id for [groupsPanel] */
    override val groupPanelId = 476876

    /** @see ParentFragment.titleRes */
    override val titleRes get() = R.string.title_movie_channels

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        channelGroupsViewModel.groups.observe {
            doOnData( ::onGroups )
            doOnError { notifier.error( it ) }
        }
    }
}