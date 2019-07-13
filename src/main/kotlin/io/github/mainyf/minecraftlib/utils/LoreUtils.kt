@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package io.github.mainyf.minecraftlib.utils

import io.github.mainyf.minecraftlib.extensions.extractText
import io.github.mainyf.minecraftlib.extensions.getLore
import org.bukkit.inventory.ItemStack
import java.util.regex.Pattern

object LoreUtils {

    private val positiveDoublePattern = Pattern.compile("(\\d+)([.]\\d+)?")
    private val negativeDoublePattern = Pattern.compile("-?(\\d+)([.]\\d+)?")

    private val positivePercentDoublePattern = Pattern.compile("(\\d+)([.]\\d+)?[%]?")
    private val negativePercentDoublePattern = Pattern.compile("-?(\\d+)([.]\\d+)?[%]?")

    private fun <T> getItemMark(item: ItemStack?, mark: String, defaultValue: T, fn: (v: T, loreItem: String) -> T?): T? {
        if (item == null) {
            return null
        }
        var result: T = defaultValue
        var hasValid = false
        item.getLore().forEach {
            if (it.matches(Regex(mark))) {
                hasValid = true
                val v = fn.invoke(result, it)
                if(v != null) {
                    result = v
                }
            }
        }
        return if (hasValid) result else null
    }

    fun getItemRangeValueDouble(item: ItemStack?, mark: String, hasNegative: Boolean = true): DoubleValueRange? {
        return getItemMark(item, mark, DoubleValueRange.empty(), { v: DoubleValueRange, loreItem: String ->
            v.mergeRange(
                convertRangeDouble(loreItem, hasNegative)
            )
            return@getItemMark v
        })
    }

    fun getItemRangeValueInt(item: ItemStack?, mark: String, hasNegative: Boolean = true): IntValueRange? {
        return getItemMark(item, mark, IntValueRange.empty(), { v: IntValueRange, loreItem: String ->
            v.mergeRange(
                convertRangeInt(loreItem, hasNegative)
            )
            return@getItemMark v
        })
    }

    fun getItemValue(item: ItemStack?, mark: String, hasNegative: Boolean = true): Double? {
        return getItemMark(item, mark, 0.0, { v: Double, loreItem: String ->
            return@getItemMark v + (loreItem.extractText(if (hasNegative) negativeDoublePattern else positiveDoublePattern) ?: return@getItemMark null).toDouble()
        })
    }

    fun getItemPercent(item: ItemStack?, mark: String, hasNegative: Boolean = true): Double? {
        return getItemMark(item, mark, 0.0, { v: Double, loreItem: String ->
            val text = loreItem.extractText(if (hasNegative) negativePercentDoublePattern else positivePercentDoublePattern) ?: return@getItemMark null
            return@getItemMark v + (text.substring(0, text.length - 1)).toDouble()
        })
    }

}