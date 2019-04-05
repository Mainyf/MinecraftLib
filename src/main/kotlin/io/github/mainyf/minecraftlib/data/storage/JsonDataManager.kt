package io.github.mainyf.minecraftlib.data.storage

import io.github.mainyf.minecraftlib.data.BaseData
import io.github.mainyf.minecraftlib.data.IDataManager
import java.io.File
import java.nio.charset.Charset
import java.util.*

class JsonDataManager<T : BaseData>(
    dataFolder: File,
    val dataKey: String,
    val type: Class<T>,
    val charset: Charset = Charsets.UTF_8
) : IDataManager<T> {

    override fun add(element: T): Boolean {
        return false
    }

    override fun addAll(element: Collection<T>) {
    }

    override fun remove(uid: Long): Boolean {
        return false
    }

    override fun remove(uuid: UUID): Boolean {
        return false
    }

    override fun removeWith(predicte: (T) -> Boolean): Boolean {
        return false
    }

    override fun update(uid: Long, element: T): Boolean {
        return false
    }

    override fun update(uuid: UUID, element: T): Boolean {
        return false
    }

    override fun update(predicte: (T) -> Boolean, element: T): Boolean {
        return false
    }

    override fun find(uid: Long): T? {
        return null
    }

    override fun find(uuid: UUID): T? {
        return null
    }

    override fun findAll(): MutableList<T> {
        return mutableListOf()
    }

    override fun findWith(predicte: (T) -> Boolean): List<T> {
        return emptyList()
    }

    override fun findPagination(pageIndex: Int, pageSize: Int, predicte: (T) -> Boolean): List<T> {
        return emptyList()
    }

}