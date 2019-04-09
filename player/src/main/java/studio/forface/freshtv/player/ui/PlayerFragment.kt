package studio.forface.freshtv.player.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import studio.forface.freshtv.commonandroid.ui.BaseFragment
import studio.forface.freshtv.player.R

/**
 * An abstract [Fragment] for [PlayerActivity]s [Fragment]s
 *
 * Inherit from [BaseFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class PlayerFragment( @LayoutRes layoutRes: Int ): BaseFragment( layoutRes ) {

    /** @return [String] Channel id from [playerActivity] */
    val channelId by lazy { playerActivity!!.channelId }
}