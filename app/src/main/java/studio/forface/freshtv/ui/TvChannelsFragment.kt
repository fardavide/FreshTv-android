package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.ui.adapters.TvChannelsAdapter
import studio.forface.freshtv.viewmodels.TvChannelsViewModel


/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for see the stored `TvChannels` with their `TvGuide`
 *
 * // TODO inherit from [NestedFragment]
 */
internal class TvChannelsFragment: RootFragment( R.layout.fragment_recycler_view ) {

    /** A key for [groupName] argument */
    companion object { const val ARG_GROUP_NAME = "extra.group-name" }

    /** A reference to [TvChannelsAdapter] for [recyclerView] */
    private val adapter = TvChannelsAdapter()

    /** A reference to [TvChannelsViewModel] for retrieve the stored `Channel`s */
    private val channelsViewModel by viewModel<TvChannelsViewModel> { parametersOf( groupName ) }

    /** An OPTIONAL [String] received from [getArguments] for filter elements by their `groupName` */
    private val groupName by lazy { arguments?.getString( ARG_GROUP_NAME ) }

    // TODO cannot import synthetic from another module
    private val recyclerView by lazy { view!!.findViewById<RecyclerView>( R.id.recyclerView ) }

    override val title: String? get() = "TODO"

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        channelsViewModel.channels.observe {
            doOnData { adapter.submitList( it ) }
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [TvChannelsFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        recyclerView.layoutManager = LinearLayoutManager( context )
        recyclerView.adapter = adapter
    }
}