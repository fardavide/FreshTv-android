package studio.forface.freshtv.about.ui

import android.content.Intent
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.os.Bundle
import android.text.Spannable.SPAN_INCLUSIVE_EXCLUSIVE
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_about.*
import studio.forface.freshtv.about.BuildConfig.VERSION_NAME
import studio.forface.freshtv.about.R
import studio.forface.freshtv.commonandroid.ui.ParentFragment
import studio.forface.freshtv.commonandroid.utils.setOnClickListener

/**
 * A `Fragment` that shows info about the App
 * Inherit from [ParentFragment]
 *
 * @author Davide Giuseppe Farella
 */
class AboutFragment : ParentFragment( R.layout.fragment_about ) {

    /** @see ParentFragment.isRootFragment */
    override val isRootFragment = true

    /** @see ParentFragment.titleRes */
    override val titleRes get() = R.string.title_about

    /** When the [AboutFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )

        // Version Name
        val rawVersionString = getString( R.string.about_version_name_arg, VERSION_NAME )
        val startBold = 0
        val endBold = rawVersionString.indexOf( VERSION_NAME )
        val versionSpannable = SpannableString( rawVersionString ).apply {
            setSpan( StyleSpan( BOLD ), startBold, endBold, SPAN_INCLUSIVE_EXCLUSIVE )
        }
        aboutVersionNameTextView.text = versionSpannable

        // Rate
        aboutRateImageView.setOnClickListener( ::showComingSoon )
        aboutRateTextView.setOnClickListener( ::showComingSoon )

        // Report Issue
        aboutReportImageView.setOnClickListener( ::showComingSoon )
        aboutReportTextView.setOnClickListener( ::showComingSoon )

        // Telegram
        aboutTelegramImageView.setOnClickListener( ::openTelegramGroup )
        aboutTelegramTextView.setOnClickListener( ::openTelegramGroup )

        // Roadmap
        aboutRoadmapRecyclerView.apply {
            adapter = ExpandableAdapter( context.resources.getStringArray( R.array.about_roadmap_values ) )
            layoutManager = LinearLayoutManager( requireContext() )
        }

        // What's new
        aboutWhatsNewRecyclerView.apply {
            adapter = ExpandableAdapter( context.resources.getStringArray( R.array.about_what_new_values ) )
            layoutManager = LinearLayoutManager( requireContext() )
        }
    }

    /** Deep-link to Telegram Group */
    private fun openTelegramGroup() {
        val uri = Uri.parse( getString( R.string.about_telegram_group_link ) )
        with( Intent( Intent.ACTION_VIEW ) ) {
            data = uri
            startActivity(this )
        }
    }

    /** Inform the user that the feature will be available soon */
    private fun showComingSoon() {
        notifier.info( getString( R.string.coming_soon ) )
    }
}