@file:Suppress("UNCHECKED_CAST")

package io.github.mainyf.minecraftlib.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.mainyf.minecraftlib.extensions.closeFinally
import org.gradle.internal.nativeintegration.filesystem.FileException
import java.nio.file.Files
import java.nio.file.Path

object YamlHelper {

    private val factory = YAMLFactory()
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS)!!

    val mapper: ObjectMapper = ObjectMapper(factory)
        .registerKotlinModule()
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

    fun saveFile(file: Path, data: Any) {
        if (Files.notExists(file)) {
            Files.createFile(file)
        }
        val writer = Files.newBufferedWriter(file)
        mapper.writeValue(writer, data)
        writer.closeFinally(Throwable("save file fail"))
    }

    inline fun <reified T> readFile(file: Path): T {
        if (Files.notExists(file)) {
            throw FileException("read file error, file not exists", Throwable())
        }
        val reader = Files.newBufferedReader(file, Charsets.UTF_8)
        val result = mapper.readValue(reader, T::class.java)
        reader.closeFinally(Throwable("read file fail"))
        return result
    }

    fun <T> readFile(file: Path, type: JavaType): T {
        if (Files.notExists(file)) {
            throw FileException("read file error, file not exists", Throwable())
        }
        val reader = Files.newBufferedReader(file, Charsets.UTF_8)
        val result = mapper.readValue<T>(reader, type)
        reader.closeFinally(Throwable("read file fail"))
        return result
    }

    fun getTypeFactory(): TypeFactory {
        return mapper.typeFactory
    }
}