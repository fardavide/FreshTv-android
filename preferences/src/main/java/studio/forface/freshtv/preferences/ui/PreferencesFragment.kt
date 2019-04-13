package studio.forface.freshtv.preferences.ui

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_preferences.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.forface.freshtv.commonandroid.ui.ParentFragment
import studio.forface.freshtv.preferences.R
import studio.forface.freshtv.preferences.viewmodels.PreferencesViewModel

/**
 * A `Fragment` for set preferences
 * Inherit from [ParentFragment]
 *
 * @author Davide Giuseppe Farella
 */
class PreferencesFragment: ParentFragment( R.layout.fragment_preferences ) {

    /** @see ParentFragment.isRootFragment */
    override val isRootFragment = true

    /** An instance of [PreferencesViewModel] */
    private val preferencesViewModel by viewModel<PreferencesViewModel>()

    /** @see ParentFragment.titleRes */
    override val titleRes get() = R.string.title_preferences

    /** When `Activity` is created for [PreferencesFragment] */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        with( preferencesViewModel ) {
            // Observe Channels
            channelsDatabaseState.observeData( preferencesCleanChannelsView::updateValue )
            // Observe Tv Guides
            guidesDatabaseState.observeData( preferencesCleanGuidesView::updateValue )
        }


    }

    /** When [View] is created for [PreferencesFragment] */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )

        // Set clean Channels
        preferencesCleanChannelsView.doOnAction { showDialog(
            R.string.clean_channels_confirmation_dialog_title,
            R.string.clean_channels_confirmation_dialog_description
        ) { preferencesViewModel.cleanChannels() } }

        // Set clean Tv Guides
        preferencesCleanGuidesView.doOnAction { showDialog(
            R.string.clean_guides_confirmation_dialog_title,
            R.string.clean_guides_confirmation_dialog_description
        ) { preferencesViewModel.cleanGuides() } }
    }
}