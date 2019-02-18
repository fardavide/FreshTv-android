package studio.forface.freshtv.localdata.sqldelight

import com.squareup.sqldelight.ColumnAdapter
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.utils.LocalDateTimeHelper
import studio.forface.freshtv.domain.utils.toEpochMillis

/** A [String] separator for divide items while encoding/decoding to/from [String] */
private const val SEPARATOR = ", "

/** A [String] separator for divide key and value while encoding/decoding to/from [String] */
private const val KEY_VALUE_DIVIDER = " : "

/** A [ColumnAdapter] for [LocalDateTime] */
object DateTimeColumnAdapter : ColumnAdapter<LocalDateTime, Long> {
    override fun encode( value: LocalDateTime ) = value.toEpochMillis()
    override fun decode( databaseValue: Long ) = LocalDateTimeHelper.ofEpochMillis( databaseValue )
}

/** A [ColumnAdapter] for [Map] of [String] and [Int] */
object StringIntMapColumnAdapter : ColumnAdapter<Map<String, Int>, String> {
    override fun encode( value: Map<String, Int> ) =
        value.toList().joinToString( separator = SEPARATOR ) { "${it.first}$KEY_VALUE_DIVIDER${it.second}" }
    override fun decode( databaseValue: String ) = databaseValue.split( SEPARATOR ).map {
        val parts = it.split( KEY_VALUE_DIVIDER )
        parts[0] to parts[1].toInt()
    }.toMap()
}

/** A [ColumnAdapter] for [List] of [String] */
object StringListColumnAdapter : ColumnAdapter<List<String>, String> {
    override fun encode( value: List<String> ) = value.joinToString( separator = SEPARATOR )
    override fun decode( databaseValue: String ) = databaseValue.split( SEPARATOR )
}