package studio.forface.freshtv.localdata.mappers

import studio.forface.freshtv.domain.entities.*

/*
 * Author: Davide Giuseppe Farella
 * A file containing Mapper's interfaces for Pojo's
 */

/** A Mapper for `ChannelGroup`s Pojo */
interface ChannelGroupPojoMapper<Pojo>: PojoMapper<ChannelGroup, Pojo>

/** A Mapper for `MovieChannel`s Pojo */
interface MovieChannelPojoMapper<Pojo>: PojoMapper<MovieChannel, Pojo>

/** A Mapper for `SourceFile`s Pojo */
interface SourceFilePojoMapper<Pojo>: PojoMapper<SourceFile, Pojo>

/** A Mapper for `TvChannel`s Pojo */
interface TvChannelPojoMapper<Pojo>: PojoMapper<TvChannel, Pojo>

/** A Mapper for `TvChannel`s with relative Guide Pojo */
interface TvChannelWithGuidePojoMapper<Pojo>: PojoMapper<TvChannelWithGuide, Pojo>

/** A Mapper for `TvGuide`s Pojo */
interface TvGuidePojoMapper<Pojo> : PojoMapper<TvGuide, Pojo>