package io.github.mainyf.minecraftlib.extensions

import java.util.regex.Pattern

fun String.replaceColorChar(variable: Map<String, Any> = mapOf()): String {
    var msg = this
    variable.mergeMap(mapOf("&" to "§")).forEach { pair -> msg = msg.replace(pair.key, pair.value.toString()) }
    return msg
}

fun String.replaceChar(variable: Map<String, Any> = mapOf()): String {
    var msg = this
    variable.forEach { pair -> msg = msg.replace(pair.key, pair.value.toString()) }
    return msg
}

fun String.extractText(regex: String): String? {
    val matcher = Pattern.compile(regex).matcher(this)
    return if(!matcher.find()) null else matcher.group()
}

fun String.extractText(pattern: Pattern): String? {
    val matcher = pattern.matcher(this)
    return if(!matcher.find()) null else matcher.group()
}