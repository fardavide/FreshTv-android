package studio.forface.freshtv.utils

import com.squareup.sqldelight.ColumnAdapter
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.utils.toEpochSecond
import studio.forface.freshtv.domain.utils.LocalDateTimeHelper as LocalDateTimeHelper

/** A [ColumnAdapter] for [LocalDateTime] */
object DateTimeAdapter : ColumnAdapter<LocalDateTime, Long> {
    override fun encode( value: LocalDateTime ) = value.toEpochSecond()
    override fun decode( databaseValue: Long ) = LocalDateTimeHelper.ofEpochSecond( databaseValue )
}