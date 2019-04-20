@file:Suppress("CAST_NEVER_SUCCEEDS")

package io.github.mainyf.minecraftlib.utils

import io.github.mainyf.minecraftlib.extensions.extractText
import java.util.regex.Pattern

object NumberUtils {

    val FLOAT_NUMBER = Pattern.compile("^(-?\\d+)([.]\\d+)?$")

    val POSITIVE_FLOAT_NUMBER = Pattern.compile("^\\d+([.]\\d+)?$")

    val NEGATIVE_FLOAT_NUMBER = Pattern.compile("^-\\d+([.]\\d+)?$")

    val NUMBER = Pattern.compile("^(-?\\d+)\\d+?$")

    val FLOAT = Pattern.compile("^(-?\\d+)([.]\\d+)$")

    val NEGATIVE_NUMBER_ONLY = Pattern.compile("^-\\d+$")

    val POSITIVE_NUMBER_ONLY = Pattern.compile("^\\d+$")

    val NEGATIVE_FLOAT_ONLY = Pattern.compile("^-\\d+([.]\\d+)$")

    val POSITIVE_FLOAT_ONLY = Pattern.compile("^\\d+([.]\\d+)$")

    val PARSE_NUMBER_REGEX = Pattern.compile("\\d+")

    val PARSE_FLOAT_REGEX = Pattern.compile("(-?\\d+)([.]\\d+)?")

    fun isNumber(str: String): Boolean {
        return NUMBER.matcher(str).matches()
    }

    fun isFloat(str: String): Boolean {
        return POSITIVE_FLOAT_NUMBER.matcher(str).matches()
    }

    fun isFloatOnly(str: String): Boolean {
        return POSITIVE_FLOAT_ONLY.matcher(str).matches()
    }

    fun parseNumber(str: String): Int? {
        val matcher = PARSE_NUMBER_REGEX.matcher(str)
        return if (matcher.find()) {
            Integer.valueOf(matcher.group())
        } else null
    }

    fun parseFloat(str: String): Float? {
        val matcher = PARSE_FLOAT_REGEX.matcher(str)
        return if (matcher.find()) {
            java.lang.Float.valueOf(matcher.group())
        } else null
    }

    fun extractInt(str: String): Int? {
        return str.extractText("(-?\\d+)\\d+?")?.toInt()
    }

    fun extractDouble(str: String): Double? {
        return str.extractText("(-?\\d+)([.]\\d+)?")?.toDouble()
    }
}
