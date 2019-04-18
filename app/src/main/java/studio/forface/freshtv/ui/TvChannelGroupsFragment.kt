package studio.forface.freshtv.ui

import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.ui.ParentFragment
import studio.forface.freshtv.viewmodels.TvChannelGroupsViewModel

/**
 * A `Fragment` for see the stored `TvChannel`s Groups
 * Inherit from [AbsChannelGroupsFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvChannelGroupsFragment :
    AbsChannelGroupsFragment( { groupName -> TvChannelsFragment( groupName ) } ) {

    companion object {
        /** @return `NavDirections` to this `Fragment` */
        fun directions() = HomeFragmentDirections.actionToTvChannelGroupsFragment()
    }

    /** A reference to [TvChannelGroupsViewModel] for retrieve the stored `Channel`s */
    override val channelGroupsViewModel by viewModel<TvChannelGroupsViewModel>()

    /** An id for [groupsPanel] */
    override val groupPanelId = 476878

    /** @see ParentFragment.titleRes */
    override val titleRes get() = R.string.title_tv_channels
}