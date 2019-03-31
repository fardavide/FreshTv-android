package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.PlayerFragment
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.domain.utils.invoke
import studio.forface.freshtv.ui.adapters.MovieChannelsAdapter
import studio.forface.freshtv.viewmodels.MovieChannelsViewModel

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for see the stored `MovieChannels`
 *
 * Inherit from [NestedFragment]
 */
internal class MovieChannelsFragment: NestedFragment<MovieChannelGroupsFragment>( R.layout.fragment_recycler_view ) {

    companion object {
        /** @return a new instance of [MovieChannelsFragment] with [groupName] in [MovieChannelsFragment.mArguments] */
        operator fun invoke( groupName: String ) = MovieChannelsFragment().apply {
            val args = bundleOf(ARG_GROUP_NAME to groupName )
            if ( arguments != null ) requireArguments().putAll( args )
            else arguments = args
        }
        /** A key for [groupName] argument */
        private const val ARG_GROUP_NAME = "extra.group-name"
    }

    /** A reference to [MovieChannelsAdapter] for `recyclerView` */
    private val adapter = MovieChannelsAdapter().apply {
        onItemClick = { navController.navigate( PlayerFragment { directions( it.id ) } ) }
        onItemFavoriteChange = { channelsViewModel.setFavoriteChannel( it ) }
    }

    /** A reference to [MovieChannelsViewModel] for retrieve the stored `Channel`s */
    private val channelsViewModel by viewModel<MovieChannelsViewModel> { parametersOf( groupName ) }

    /** An OPTIONAL [String] received from [getArguments] for filter elements by their `groupName` */
    private val groupName by lazy { requireArguments().getString( ARG_GROUP_NAME )!! }

    /** A reference to the [ContentLoadingProgressBar] */
    private val progressBar get() = requireView().findViewById<ContentLoadingProgressBar>( R.id.progressBar )

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        channelsViewModel.channels.observe {
            doOnData { adapter.submitList( it ) }
            doOnError { notifier.error( it ) }
            doOnLoadingChange( ::onLoading )
        }
    }

    /** When the [MovieChannelsFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        val recyclerView = view.findViewById<RecyclerView>( R.id.recyclerView )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager( context )
    }

    /** Update the loading state */
    private fun onLoading( loading: Boolean ) {
        if ( loading ) progressBar.show() else progressBar.hide()
    }
}