package studio.forface.freshtv.uimodels

import androidx.annotation.StringRes

/**
 * @author Davide Giuseppe Farella
 * An Ui Model representing info for a Form for edit a Source File: Playlist or Epg.
 */
data class SourceFileEditFormUiModel (
        val name: CharSequence? = null,
        val path: CharSequence? = null,
        @StringRes val urlError: Int? = null
)