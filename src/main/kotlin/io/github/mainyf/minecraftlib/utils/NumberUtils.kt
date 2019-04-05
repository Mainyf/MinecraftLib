package io.github.mainyf.minecraftlib.utils

import java.util.regex.Pattern

object NumberUtils {

    private val FLOAT_NUMBER = Pattern.compile("^(-?\\d+)([.]\\d+)?$")

    private val POSITIVE_FLOAT_NUMBER = Pattern.compile("^\\d+([.]\\d+)?$")

    private val NEGATIVE_FLOAT_NUMBER = Pattern.compile("^-\\d+([.]\\d+)?$")

    private val NUMBER = Pattern.compile("^(-?\\d+)\\d+?$")

    private val FLOAT = Pattern.compile("^(-?\\d+)([.]\\d+)$")

    private val NEGATIVE_NUMBER_ONLY = Pattern.compile("^-\\d+$")

    private val POSITIVE_NUMBER_ONLY = Pattern.compile("^\\d+$")

    private val NEGATIVE_FLOAT_ONLY = Pattern.compile("^-\\d+([.]\\d+)$")

    private val POSITIVE_FLOAT_ONLY = Pattern.compile("^\\d+([.]\\d+)$")

    private val PARSE_NUMBER_REGEX = Pattern.compile("\\d+")

    private val PARSE_FLOAT_REGEX = Pattern.compile("(-?\\d+)([.]\\d+)?")

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

}
