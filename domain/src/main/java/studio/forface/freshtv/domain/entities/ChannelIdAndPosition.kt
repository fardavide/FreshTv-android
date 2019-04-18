package studio.forface.freshtv.domain.entities

/** A typealias for a [Pair] of [String] and [Int] for a `Channel` */
typealias ChannelIdAndPosition = Pair<String, Int>

val ChannelIdAndPosition.id get() = first
val ChannelIdAndPosition.position get() = second