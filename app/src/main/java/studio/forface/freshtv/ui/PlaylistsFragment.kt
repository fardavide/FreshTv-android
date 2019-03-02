package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.viewModel
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.ui.HomeFragmentDirections.Companion.actionToEditPlaylistFragment
import studio.forface.freshtv.ui.adapters.SourceFilesAdapter
import studio.forface.freshtv.viewmodels.PlaylistsViewModel

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for see the stored `Playlists`
 *
 * Inherit from [RootFragment]
 */
class PlaylistsFragment: RootFragment( R.layout.fragment_recycler_view ) {

    /** A reference to [SourceFilesAdapter] for [recyclerView] */
    private val adapter = SourceFilesAdapter().apply {
        onItemClick = { navController.navigate( actionToEditPlaylistFragment( it.fullPath ) ) }
    }

    /** @see RootFragment.fabParams */
    override val fabParams: FabParams get() = FabParams(
        R.drawable.ic_new_black,
        R.string.action_add_playlist
    ) { navController.navigate( actionToEditPlaylistFragment(null ) ) }

    /** A reference to [PlaylistsViewModel] for retrieve the stored `Playlist`s */
    private val playlistsViewModel by viewModel<PlaylistsViewModel>()

    // TODO cannot import synthetic from another module
    private val recyclerView by lazy { view!!.findViewById<RecyclerView>( R.id.recyclerView ) }

    /** @see RootFragment.titleRes */
    override val titleRes get() = R.string.menu_my_playlists

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        playlistsViewModel.playlists.observe {
            doOnData { adapter.submitList( it ) }
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [PlaylistsFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager( context )
    }
}