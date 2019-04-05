package io.github.mainyf.minecraftlib.data.storage

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.mainyf.minecraftlib.data.BaseData
import io.github.mainyf.minecraftlib.data.IDataManager
import io.github.mainyf.minecraftlib.extensions.*
import java.io.BufferedReader
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@Suppress("UNCHECKED_CAST", "UNUSED_EXPRESSION", "UNREACHABLE_CODE")
class YamlDataManager<T : BaseData>(
    dataFolder: File,
    dataKey: String,
    private val type: Class<T>,
    private val charset: Charset = Charsets.UTF_8
) : IDataManager<T> {

    val salt = "$*&.][-=}|yaml"
    var dataDir: Path = dataFolder.toPath().resolve(dataKey)

    var mapper: ObjectMapper
    private val cacheDatas = mutableListOf<T>()

    init {
        val factory = YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
            .enable(YAMLGenerator.Feature.INDENT_ARRAYS)

        mapper = ObjectMapper(factory)
            .registerKotlinModule()
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

        if(Files.notExists(this.dataDir)) {
            Files.createDirectories(this.dataDir)
        }
        val directoryStream = Files.newDirectoryStream(this.dataDir)
        var buffer: BufferedReader? = null
        directoryStream.forEach {
            buffer = Files.newBufferedReader(it, charset)
            cacheDatas.add(mapper.readValue(buffer, type))
        }
        directoryStream.closeFinally(Throwable("closing directory stream error"))
        buffer?.closeFinally(Throwable("closing buffer error"))
    }

    override fun add(element: T): Boolean {
        val fixedElement = assignBaseValue(element)
        val file = dataDir.resolve(getFinalName(fixedElement))
        if(Files.exists(file) || find(fixedElement.uuid!!) != null) {
            return false
        }
        Files.createFile(file)

        save(fixedElement, file)

        cacheDatas.add(fixedElement)

        return true
    }

    private fun save(element: T, file: Path) = Files.newBufferedWriter(file, charset).use {
        mapper.writeValue(it, element)
    }

    override fun addAll(element: Collection<T>) = element.forEach { add(it) }

    override fun remove(uid: Long): Boolean = removeWith { it.uid == uid }

    override fun remove(uuid: UUID): Boolean = removeWith { it.uuid == uuid }

    override fun removeWith(predicte: (T) -> Boolean): Boolean {
        val element: T? = cacheDatas.getWithFirst(predicte) ?: return false
        val file = dataDir.resolve(getFinalName(element!!))
        if(Files.notExists(file)) {
            return false
        }

        Files.delete(file)
        return true
    }

    override fun update(uid: Long, element: T): Boolean = update({ it.uid == uid }, element)

    override fun update(uuid: UUID, element: T): Boolean = update({ it.uuid == uuid }, element)

    override fun update(predicte: (T) -> Boolean, element: T): Boolean {
        val oldElements = cacheDatas.getWith(predicte)
        if(oldElements.isEmpty()) {
            return false
        }
        oldElements.forEach {currentEl ->
            val newData = currentEl.copyDataTo(element)
            val file = dataDir.resolve(getFinalName(newData as T))
            if(Files.notExists(file)) {
                return false
            }

            save(newData, file)

            cacheDatas.setWithFirst({ it.uuid == newData.uuid }, newData)
        }
        return true
    }

    override fun find(uid: Long): T? {
        val predicteList = findWith { it.uid == uid }
        return if(predicteList.isEmpty()) null else predicteList.first()
    }

    override fun find(uuid: UUID): T? {
        val predicteList = findWith { it.uuid == uuid }
        return if(predicteList.isEmpty()) null else predicteList.first()
    }

    override fun findAll(): MutableList<T> {
        return cacheDatas.toMutableList()
    }

    override fun findWith(predicte: (T) -> Boolean): List<T> {
        return cacheDatas.getWith(predicte)
    }

    override fun findPagination(pageIndex: Int, pageSize: Int, predicte: (T) -> Boolean): List<T> {
        return findWith(predicte).pagination(pageIndex, pageSize)
    }

    private fun assignBaseValue(element: T): T {
        element.uid = cacheDatas.size.toLong()
        element.uuid = UUID.randomUUID()
        element.createTime = Date(System.currentTimeMillis())
        return element
    }

    private fun getFinalName(element: T): String {
        return "${element.uuid.toString()}.yml"
    }

}