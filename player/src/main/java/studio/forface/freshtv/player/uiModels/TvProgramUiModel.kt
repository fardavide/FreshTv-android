package studio.forface.freshtv.player.uiModels

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.annotation.StringRes
import androidx.core.text.toSpannable
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import studio.forface.freshtv.commonandroid.utils.append

/** A data class containing info of a Program of the current `TvChannel` */
internal data class TvProgramUiModel(
    val title: String,
    val description: String,
    val image: String?,
    val optionalInformations: OptionalInformations,
    val director: String?,
    val actors: String?
) {
    /** A data class containing [OptInfo]s for [TvProgramUiModel] */
    data class OptionalInformations(
        val category: OptInfo?,
        val year: OptInfo?,
        val country: OptInfo?,
        val rating: OptInfo?
    ) {
        /** @return a [List] of all NOT NULL [OptInfo] for this element */
        fun list() = listOfNotNull( category, year, country, rating )

        /** @return `true` if at least on item is not `null` */
        fun isNotEmpty() = category != null || year != null || country != null || rating != null
    }

    /** A data class for an optional info, containing its name and value */
    data class OptInfo( @StringRes val name: Int, val value: String ) {

        /** @return a [Spannable] String from test of [name] and *bold* [value] */
        fun stringify( context: Context) = SpannableStringBuilder( context.getText( name ) )
            .append( ": " )
            .append( value, StyleSpan( Typeface.BOLD ) )
            .toSpannable()
    }
}


/** Set a [TvProgramUiModel.OptionalInformations] inside a [ChipGroup] */
internal fun ChipGroup.setOptionalInformations( informations: TvProgramUiModel.OptionalInformations ) {
    if ( informations.isNotEmpty() ) isVisible = true else return
    informations.list().forEach {
        val chip = Chip( context )
        chip.setOptInfo( it )
        this.addView( chip )
    }
}

/** Set a [TvProgramUiModel.OptInfo] inside a [Chip.setText] */
internal fun Chip.setOptInfo(info: TvProgramUiModel.OptInfo ) {
    text = info.stringify( context )
}