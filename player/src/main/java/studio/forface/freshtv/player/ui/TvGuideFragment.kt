package studio.forface.freshtv.player.ui

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_tv_guide.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.uiModels.TvProgramUiModel
import studio.forface.freshtv.player.uiModels.setOptionalInformations
import studio.forface.freshtv.player.viewmodels.TvGuideViewModel
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.theia

/**
 * A [PlayerFragment] for the info for [PlayerActivity]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvGuideFragment : PlayerFragment( R.layout.fragment_tv_guide ) {

    companion object {
        /** @return a new instance of [TvGuideFragment] with [groupName] in [TvGuideFragment.mArguments] */
        operator fun invoke( groupName: String ) = TvGuideFragment().apply {
            val args = bundleOf(ARG_GUIDE_ID to groupName )
            if ( arguments != null ) requireArguments().putAll( args )
            else arguments = args
        }
        /** A key for [guideId] argument */
        private const val ARG_GUIDE_ID = "extra.guide-id"
    }

    /** The id of the Guide to retrieve */
    private val guideId by lazy { requireArguments().getString( ARG_GUIDE_ID )!! }

    /** A reference to [TvGuideViewModel] for get [TvProgramUiModel] */
    private val viewModel by viewModel<TvGuideViewModel> { parametersOf( guideId ) }

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )
        viewModel.program.observe {
            doOnData( ::onGuide )
            doOnError { notifier.error( it ) }
        }
    }

    /** When [TvProgramUiModel] is received from [TvGuideViewModel] */
    private fun onGuide( guide: TvProgramUiModel ) {
        with<TvProgramUiModel, Unit>( guide ) {
            tvGuideTitleTextView.text = title
            tvGuideDescriptionTextView.text = description
            image?.let {
                tvGuideImageView.isVisible = true
                tvGuideImageView.theia { imageUrl = it }
            }
            tvGuideOptionalInformationsChipGroup.setOptionalInformations( optionalInformations )
            director?.let {
                tvGuideDirectorTextView.isVisible = true
                tvGuideDirectorValueTextView.text = it
            }
            actors?.let {
                tvGuideActorsTextView.isVisible = true
                tvGuideActorsValueTextView.text = it
            }
        }
    }
}