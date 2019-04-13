package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.ui.ParentFragment
import studio.forface.freshtv.ui.adapters.SourceFilesAdapter
import studio.forface.freshtv.viewmodels.EpgsViewModel

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for see the stored `EPG`
 *
 * Inherit from [ParentFragment]
 */
class EpgsFragment: ParentFragment( R.layout.fragment_recycler_view ) {

    companion object {
        /** @return `NavDirections` to this `Fragment` */
        fun directions() = HomeFragmentDirections.actionToEpgsFragment()
    }

    /** A reference to [SourceFilesAdapter] for `recyclerView` */
    private val adapter = SourceFilesAdapter().apply {
        onItemClick = { navController.navigate( EditEpgFragment.directions( it.fullPath ) ) }
    }

    /** A reference to [EpgsViewModel] for retrieve the stored `Playlist`s */
    private val epgsViewModel by viewModel<EpgsViewModel>()

    /** @see ParentFragment.fabParams */
    override val fabParams: FabParams get() = FabParams(
        R.drawable.ic_new_black,
        R.string.action_add_epg
    ) { navController.navigate( EditEpgFragment.directions() ) }

    /** @see ParentFragment.isRootFragment */
    override val isRootFragment = true

    /** @see ParentFragment.titleRes */
    override val titleRes get() = R.string.menu_my_epgs

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        epgsViewModel.epgs.observe {
            doOnData { adapter.submitList( it ) }
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [EpgsFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        val recyclerView = view.findViewById<RecyclerView>( R.id.recyclerView )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager( context )
    }
}