package studio.forface.freshtv.player.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.fragment_tv_info.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel
import studio.forface.freshtv.player.viewmodels.ChannelInfoViewModel

/**
 * A [NestedFragment] for the info for [PlayerFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvInfoFragment : NestedFragment<PlayerFragment>( R.layout.fragment_tv_info ) {

    /** A reference to [TvProgramsAdapter] from [tvInfoViewPager] */
    private val adapter by lazy { TvProgramsAdapter( requireFragmentManager() ) }

    /** @return [String] Channel id from [InfoFragment] */
    private val channelId by lazy { parentBaseFragment.channelId }

    /** A reference to [ChannelInfoViewModel] */
    private val viewModel by viewModel<ChannelInfoViewModel> { parametersOf( channelId ) }

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        viewModel.info.observe {
            doOnData( ::onInfo )
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [TvInfoFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        tvInfoViewPager.adapter = adapter
        tvInfoTabLayout.setupWithViewPager( tvInfoViewPager )
    }

    /** When [ChannelInfoUiModel]s are received */
    private fun onInfo( info: ChannelInfoUiModel ) {
        if ( info !is ChannelInfoUiModel.Tv ) throw AssertionError()
        tvInfoNoGuideTextView.isVisible = info.programs.isEmpty()
        adapter.programs = info.programs
        tvInfoViewPager.apply {
            isVisible = info.programs.isNotEmpty()
            currentItem = info.currentIndex
        }
    }

    /** A [FragmentStatePagerAdapter] for `TvGuide`s */
    private class TvProgramsAdapter( fragmentManager: FragmentManager) : FragmentStatePagerAdapter( fragmentManager ) {

        /** A [List] of [ChannelInfoUiModel.Tv.Program] */
        var programs: List<ChannelInfoUiModel.Tv.Program> = emptyList()
            set( value ) {
                field = value
                notifyDataSetChanged()
            }

        /** @return size of [programs] */
        override fun getCount() = programs.size

        /**
         * @return a new instance of [TvGuideFragment], with [ChannelInfoUiModel.Tv.Program.tvGuideId] of item at the
         * given [position], in [TvGuideFragment.mArguments]
         */
        override fun getItem( position: Int ) = TvGuideFragment( programs[position].tvGuideId )

        /** @return [ChannelInfoUiModel.Tv.Program.headerName] for item at the given [position] */
        override fun getPageTitle( position: Int ) = programs[position].headerName
    }
}