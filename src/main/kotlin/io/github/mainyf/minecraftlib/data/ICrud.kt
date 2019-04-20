package io.github.mainyf.minecraftlib.data

import java.util.*

interface ICrud<T : BaseData> {

    fun add(element: T): Boolean

    fun addAll(element: Collection<T>)


    fun remove(uid: Long): Boolean

    fun remove(uuid: UUID): Boolean

    fun removeWith(predicte: (T) -> Boolean): Boolean


    fun update(uid: Long, element: T): Boolean

    fun update(uuid: UUID, element: T): Boolean

    fun update(predicte: (T) -> Boolean, element: T): Boolean


    fun find(uid: Long): T?

    fun find(uuid: UUID): T?

    fun findAll(): MutableList<T>

    fun findWith(predicte: (T) -> Boolean): List<T>

    fun findPagination(pageIndex: Int, pageSize: Int, predicte: (T) -> Boolean): List<T>
}