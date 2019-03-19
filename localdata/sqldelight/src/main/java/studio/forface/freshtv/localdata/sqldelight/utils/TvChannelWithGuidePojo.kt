package studio.forface.freshtv.localdata.sqldelight.utils

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.localdata.sqldelight.SelectPagedByGroupWithGuide

/** Typealias for [SelectPagedByGroupWithGuide] */
typealias TvChannelWithGuidePojo = SelectPagedByGroupWithGuide

/** @constructor for [SelectPagedByGroupWithGuide] */
@Suppress("FunctionName")
fun TvChannelWithGuidePojo(
    id: String,
    name: String,
    imageUrl: String?,
    favorite: Boolean,
    programTitle: String?,
    programStatTime: LocalDateTime?,
    programEndTime: LocalDateTime?
) = SelectPagedByGroupWithGuide.Impl(
    id, name, imageUrl, favorite, programTitle, programStatTime, programEndTime
)