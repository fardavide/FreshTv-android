package studio.forface.freshtv.androiddatabase

import androidx.paging.DataSource
import studio.forface.freshtv.localdata.mappers.PojoMapper

/*
 * Author: Davide Giuseppe Farella
 * Extensions for `PojoMapper` that cannot be declared in `localdata`, maybe because of missing dependencies.
 */

/**
 * Call [DataSource.Factory.map] passing a [PojoMapper] as receiver for the lambda [block]
 *
 * E.g. `myDataSourceOfT.map( myTMapper ) { it.toPojo() }`
 */
inline fun <T, V, E, P, M: PojoMapper<E, P>> DataSource.Factory<Int, T>.map(
    mapper: M,
    crossinline block: M.(T) -> V
) = map { mapper.block( it ) }