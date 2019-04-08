package studio.forface.freshtv.player.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_player_info.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.IChannel.Type.MOVIE
import studio.forface.freshtv.domain.entities.IChannel.Type.TV
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.viewmodels.ChannelInfoViewModel
import studio.forface.freshtv.player.viewmodels.ChannelTypeViewModel

/**
 * A [NestedFragment] for the info for [PlayerFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class InfoFragment : NestedFragment<PlayerFragment>( R.layout.fragment_player_info ) {

    /** @return [String] Channel id from [parentBaseFragment] */
    val channelId: String by lazy { parentBaseFragment.channelId }

    /** A reference to [ChannelInfoViewModel] */
    private val viewModel by viewModel<ChannelTypeViewModel> { parametersOf( channelId ) }

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        viewModel.type.observe {
            doOnData( ::onType )
            doOnError { notifier.error( it ) }
        }
    }

    /** When [IChannel.Type] is received from [ChannelTypeViewModel] */
    private fun onType( info: IChannel.Type ) {
        val fragment = when( info ) {
            MOVIE -> MovieInfoFragment()
            TV -> TvInfoFragment()
        }
        requireFragmentManager().beginTransaction()
            .add( R.id.fragmentPlayerInfoContainer, fragment )
            .commit()
    }
}