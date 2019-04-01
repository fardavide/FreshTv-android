package studio.forface.freshtv.player.uiModels

import android.content.Context
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.annotation.StringRes
import androidx.core.text.toSpannable
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import studio.forface.freshtv.commonandroid.utils.append
import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel.Tv.Program

/**
 * A sealed class representing additional info about the current `Channel`
 *
 * @author Davide Giuseppe Farella
 */
sealed class ChannelInfoUiModel {

    /** A data class containing info of the current `MovieChannel` TODO */
    object Movie : ChannelInfoUiModel()

    /**
     * A data class containing a [List] of [Program] of the current `TvChannel`
     *
     * @param currentIndex an [Int] of the index of the current [Program] ( by its [Program.startTime] and
     * [Program.endTime] )
     */
    data class Tv( val programs: List<Program>, val currentIndex: Int ) : ChannelInfoUiModel() {

        /** A data class containing info of a Program of the current `TvChannel` */
        data class Program(
            val title: String,
            val description: String,
            val image: String?,
            val optionalInformations: OptionalInformations,
            val director: String?,
            val actors: String?,
            val startTime: String,
            val endTime: String
        ) {
            /** A data class containing [OptInfo]s for [Program] */
            data class OptionalInformations(
                val category: OptInfo?,
                val year: OptInfo?,
                val country: OptInfo?,
                val rating: OptInfo?
            ) {
                /** @return a [List] of all NOT NULL [OptInfo] for this element */
                fun list() = listOfNotNull( category, year, country, rating )
            }

            /** A data class for an optional info, containing its name and value */
            data class OptInfo( @StringRes val name: Int, val value: String ) {

                /** @return a [Spannable] String from test of [name] and *bold* [value] */
                fun stringify( context: Context ) = SpannableStringBuilder( context.getText( name ) )
                    .append( ": " )
                    .append( value, StyleSpan( BOLD ) )
                    .toSpannable()
            }
        }
    }
}

/** Set a [Program.OptionalInformations] inside a [ChipGroup] */
internal fun ChipGroup.setOptionalInformations( informations: Program.OptionalInformations ) {
    informations.list().forEach {
        val chip = Chip( context )
        chip.setOptInfo( it )
        this.addView( chip )
    }
}

/** Set a [Program.OptInfo] inside a [Chip.setText] */
internal fun Chip.setOptInfo( info: Program.OptInfo ) {
    text = info.stringify( context )
}