package studio.forface.freshtv.ui

import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_source_file_edit.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.ui.AbsEditSourceFileFragment.Mode.CREATE
import studio.forface.freshtv.ui.AbsEditSourceFileFragment.Mode.EDIT
import studio.forface.freshtv.viewmodels.AbsEditSourceFileViewModel
import studio.forface.freshtv.viewmodels.EditEpgViewModel
import studio.forface.materialbottombar.panels.params.titleColorRes
import studio.forface.materialbottombar.panels.params.titleSpSize
import studio.forface.materialbottombar.panels.params.titleTextRes

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for Edit or Create an `EPG`
 *
 * Inherit from [AbsEditSourceFileFragment]
 */
internal class EditEpgFragment : AbsEditSourceFileFragment<EditEpgViewModel>() {

    companion object {
        /** @return `NavDirections` to this `Fragment` */
        fun directions( epgPath: String? = null ) =
                HomeFragmentDirections.actionToEditEpgFragment( epgPath )
    }

    /**
     * A reference to [EditEpgFragmentArgs] for get the `epgPath` of the current editing `Playlist`
     * from [navArgs]
     */
    override val args by navArgs<EditEpgFragmentArgs>()

    /**
     * A [StringRes] representing the message to show when asking for deletion confirmation
     * @see onOptionsItemSelected
     */
    override val confirmDeletionMessageRes get() = R.string.prompt_delete_epg

    /** A reference to [EditEpgViewModel] for edit an `EPG` element */
    override val editViewModel
            by viewModel<EditEpgViewModel> { parametersOf( filePath ) }

    /** @return an OPTIONAL [String] File Path from [EditEpgFragmentArgs.epgPath] */
    override val filePath get() = args.epgPath

    /** @see RootFragment.titleRes */
    override val titleRes: Int? get() = when ( mode ) {
        CREATE -> R.string.title_add_epg
        EDIT ->   R.string.title_edit_epg
    }

    /** Setup the Texts of Views */
    override fun setupViewTexts() {
        editSourceFilePromptTextView.setText( R.string.load_epg_from )
        editSourceFileNameLayout.hint = getText( R.string.prompt_epg_name )
        editSourceFilePathLayout.hint = getText( R.string.prompt_epg_path )
        editSourceFileUrlLayout.hint = getText( R.string.prompt_epg_url )
    }

    /** A function called when [AbsEditSourceFileViewModel.state] is [AbsEditSourceFileFragment.State.SaveCompleted] */
    override fun onSaveComplete() {
        showDialog( R.string.refresh_tv_guides_delayed_title, R.string.refresh_tv_guides_delayed_message ) {
            dismissPanel()
            navController.popBackStack()
        }
    }
}