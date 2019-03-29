package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_view_pager_tabbed.*
import org.koin.androidx.viewmodel.ext.viewModel
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.uimodels.TvChannelGroupUiModel
import studio.forface.freshtv.viewmodels.TvChannelGroupsViewModel

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for see the stored `TvChannel`s Groups
 *
 * Inherit from [RootFragment]
 */
internal class TvChannelGroupsFragment: RootFragment( R.layout.fragment_view_pager_tabbed ) {

    companion object {
        /** @return `NavDirections` to this `Fragment` */
        fun directions() = HomeFragmentDirections.actionToTvChannelGroupsFragment()
    }

    /** A reference to [GroupsAdapter] for [viewPager] */
    private val adapter by lazy { GroupsAdapter( requireFragmentManager() ) }

    /** A reference to [TvChannelGroupsViewModel] for retrieve the stored `Channel`s */
    private val channelGroupsViewModel by viewModel<TvChannelGroupsViewModel>()

    /** Reference to [TabLayout] */
    private val tabLayout get() = requireView().findViewById<TabLayout>( R.id.tabLayout )

    /** Reference to [ViewPager] */
    private val viewPager get() = requireView().findViewById<ViewPager>( R.id.viewPager )

    /** @see RootFragment.titleRes */
    override val titleRes get() = R.string.title_tv_channels

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        channelGroupsViewModel.groups.observe {
            doOnData( ::onGroups )
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [TvChannelGroupsFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager( viewPager )
    }

    /** When [TvChannelGroupUiModel]s are received */
    private fun onGroups( groups: List<TvChannelGroupUiModel> ) {
        tabLayout.tabMode = if ( groups.size >= 4 ) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
        adapter.groups = groups
    }

    /** A [FragmentStatePagerAdapter] for `TvChannel`s Groups */
    private class GroupsAdapter( fragmentManager: FragmentManager ): FragmentStatePagerAdapter( fragmentManager ) {

        /** A [List] of available [TvChannelGroupUiModel] */
        var groups: List<TvChannelGroupUiModel> = emptyList()
            set( value ) {
                field = value
                notifyDataSetChanged()
            }

        /** @return size of [groups] */
        override fun getCount() = groups.size

        /**
         * @return a new instance of [TvChannelsFragment], with [TvChannelGroupUiModel.name] of item at the given
         * [position], in [TvChannelsFragment.mArguments]
         */
        override fun getItem( position: Int ) = TvChannelsFragment( groups[position].name )

        /** @return [TvChannelGroupUiModel.name] for item at the given [position] */
        override fun getPageTitle( position: Int ) = groups[position].name
    }
}