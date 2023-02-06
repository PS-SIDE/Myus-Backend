
package io.paku.myus.base

internal interface Mapper<D, E> {
    fun mapToData(from: D): E
}