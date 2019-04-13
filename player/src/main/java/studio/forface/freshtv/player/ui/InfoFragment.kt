package studio.forface.freshtv.player.ui

import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.IChannel.Type.MOVIE
import studio.forface.freshtv.domain.entities.IChannel.Type.TV
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.viewmodels.ChannelInfoViewModel
import studio.forface.freshtv.player.viewmodels.ChannelTypeViewModel

/**
 * A [PlayerFragment] for the info for [PlayerActivity]
 *
 * @author Davide Giuseppe Farella
 */
internal class InfoFragment : PlayerFragment( R.layout.fragment_player_info ) {

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