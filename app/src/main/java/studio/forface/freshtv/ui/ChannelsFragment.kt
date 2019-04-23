@file:Suppress("LeakingThis") // createAdapter

package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.PlayerFragment
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.ui.NestedFragment
import studio.forface.freshtv.domain.entities.ChannelIdAndPosition
import studio.forface.freshtv.domain.entities.id
import studio.forface.freshtv.domain.entities.position
import studio.forface.freshtv.domain.utils.invoke
import studio.forface.freshtv.ui.adapters.AbsChannelsAdapter
import studio.forface.freshtv.ui.adapters.MovieChannelsAdapter
import studio.forface.freshtv.ui.adapters.TvChannelsAdapter
import studio.forface.freshtv.uimodels.ChannelUiModel
import studio.forface.freshtv.uimodels.MovieChannelUiModel
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.freshtv.viewmodels.ChannelsViewModel
import studio.forface.freshtv.viewmodels.MovieChannelsViewModel
import studio.forface.freshtv.viewmodels.TvChannelsViewModel

/**
 * An abstract `Fragment` for see the stored `Channels`
 * Inherit from [NestedFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal sealed class ChannelsFragment<UiModel : ChannelUiModel> :
    NestedFragment<MovieChannelGroupsFragment>( R.layout.fragment_recycler_view ) {

    protected companion object {
        /** A key for [groupName] argument */
        const val ARG_GROUP_NAME = "extra.group-name"
    }

    /** A reference to [AbsChannelsAdapter] for `recyclerView` */
    private val adapter = createAdapter().apply {
        onItemClick = { navController.navigate( PlayerFragment { directions( it.id ) } ) }
        onItemFavoriteChange = { channelsViewModel.setFavoriteChannel( it ) }
    }

    /** A reference to [ChannelsViewModel] for retrieve the stored `Channel`s */
    protected abstract val channelsViewModel: ChannelsViewModel

    /** An OPTIONAL [String] received from [getArguments] for filter elements by their `groupName` */
    protected val groupName by lazy { requireArguments().getString( ARG_GROUP_NAME )!! }

    /** A reference to the [ContentLoadingProgressBar] */
    private val progressBar get() = requireView().findViewById<ContentLoadingProgressBar>( R.id.progressBar )

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        channelsViewModel.lastPosition.observeData( ::onChannelPosition )
    }

    /** When the [ChannelsFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )

        // Set the adapter
        val recyclerView = view.findViewById<RecyclerView>( R.id.recyclerView )
        recyclerView.adapter = adapter

        // Set layout manager
        val layoutManager = LinearLayoutManager( context )
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener( object : RecyclerView.OnScrollListener() {

            /**
             * Callback method to be invoked when RecyclerView's scroll state changes.
             *
             * @param recyclerView The RecyclerView whose scroll state has changed.
             * @param newState     The updated scroll state. One of {@link #SCROLL_STATE_IDLE},
             *                     {@link #SCROLL_STATE_DRAGGING} or {@link #SCROLL_STATE_SETTLING}.
             */
            override fun onScrollStateChanged( recyclerView: RecyclerView, newState: Int ) {
                super.onScrollStateChanged( recyclerView, newState )
                if ( newState != RecyclerView.SCROLL_STATE_IDLE ) return

                val firstPosition = layoutManager.findFirstVisibleItemPosition()
                val channelId = adapter.getChannelId( firstPosition )
                channelsViewModel.saveLastChannelIdAndPosition( channelId, firstPosition )
            }
        } )
    }

    /** @return a new [AbsChannelsAdapter] */
    protected abstract fun createAdapter(): AbsChannelsAdapter<UiModel, *>

    /** When channels are received from [viewModel] */
    protected fun onChannels( channels: PagedList<UiModel> ) {
        adapter.submitList( channels )
        channelsViewModel.requestLastPosition()
    }

    /** When last channel's [ChannelIdAndPosition] is received from [viewModel] */
    private fun onChannelPosition( idAndPosition: ChannelIdAndPosition ) {
        val recyclerView = view?.findViewById<RecyclerView>( R.id.recyclerView ) ?: return
        with( idAndPosition ) {
            if ( id == adapter.getChannelId( position ) )
                recyclerView.scrollToPosition( position )
        }
    }

    /** Update the loading state */
    protected fun onLoading( loading: Boolean ) {
        if ( loading ) progressBar.show() else progressBar.hide()
    }
}

/**
 * A `Fragment` for see the stored `MovieChannels`
 * Inherit from [ChannelsFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class MovieChannelsFragment: ChannelsFragment<MovieChannelUiModel>() {

    companion object {
        /** @return a new instance of [MovieChannelsFragment] with [groupName] in [MovieChannelsFragment.mArguments] */
        operator fun invoke( groupName: String ) = MovieChannelsFragment().apply {
            val args = bundleOf(ARG_GROUP_NAME to groupName )
            if ( arguments != null ) requireArguments().putAll( args )
            else arguments = args
        }
    }

    /** A reference to [MovieChannelsViewModel] for retrieve the stored `Channel`s */
    override val channelsViewModel by viewModel<MovieChannelsViewModel> { parametersOf( groupName ) }

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        channelsViewModel.channels.observe {
            doOnData( ::onChannels )
            doOnError { notifier.error( it ) }
            doOnLoadingChange( ::onLoading )
        }
    }

    /** @return a new [AbsChannelsAdapter] */
    override fun createAdapter() = MovieChannelsAdapter()
}

/**
 * A `Fragment` for see the stored `TvChannels` with their `TvGuide`
 * Inherit from [ChannelsFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvChannelsFragment: ChannelsFragment<TvChannelUiModel>() {

    companion object {
        /** @return a new instance of [TvChannelsFragment] with [groupName] in [TvChannelsFragment.mArguments] */
        operator fun invoke( groupName: String ) = TvChannelsFragment().apply {
            val args = bundleOf(ARG_GROUP_NAME to groupName )
            if ( arguments != null ) requireArguments().putAll( args )
            else arguments = args
        }
    }

    /** A reference to [TvChannelsViewModel] for retrieve the stored `Channel`s */
    override val channelsViewModel by viewModel<TvChannelsViewModel> { parametersOf( groupName ) }

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        channelsViewModel.channels.observe {
            doOnData( ::onChannels )
            doOnError { notifier.error( it ) }
            doOnLoadingChange( ::onLoading )
        }
    }

    /** @return a new [AbsChannelsAdapter] */
    override fun createAdapter() = TvChannelsAdapter()
}