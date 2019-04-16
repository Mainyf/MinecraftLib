package io.github.mainyf.minecraftlib.utils

import io.github.mainyf.minecraftlib.extensions.add
import io.github.mainyf.minecraftlib.extensions.subtract
import io.github.mainyf.minecraftlib.utils.RandomUtils
import java.math.BigDecimal
import java.util.regex.Pattern

abstract class AbstractValueRange<T : Number>(var min: T, var max: T) : Cloneable {

    abstract fun getRandomValue(): T

    abstract fun mergeRange(range: AbstractValueRange<T>)

    override fun toString(): String {
        return "AbstractValueRange(min=$min, max=$max)"
    }

    override fun hashCode(): Int {
        return super.hashCode() * min.hashCode() + max.hashCode()
    }
}

@Suppress("UNCHECKED_CAST")
class IntValueRange(min: Int, max: Int) : AbstractValueRange<Int>(min, max) {

    init {
        if (this.min > this.max) {
            this.max ^= this.min
            this.min ^= this.max
            this.max ^= this.min
        }
    }

    companion object {
        fun empty(): IntValueRange {
            return IntValueRange(0, 0).clone() as IntValueRange
        }
    }

    override fun getRandomValue(): Int {
        return RandomUtils.randomInt(min, max)
    }

    override fun mergeRange(range: AbstractValueRange<Int>) {
        this.min += range.min
        this.max += range.max
    }

}

class DoubleValueRange(min: Double, max: Double) : AbstractValueRange<Double>(min, max) {

    companion object {
        fun empty(): DoubleValueRange {
            return DoubleValueRange(0.0, 0.0).clone() as DoubleValueRange
        }
    }

    init {
        if (this.min > this.max) {
            this.max ^= this.min
            this.min ^= this.max
            this.max ^= this.min
        }
    }

    override fun getRandomValue(): Double {
        val result = BigDecimal(RandomUtils.randomDouble(min, max))
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    override fun mergeRange(range: AbstractValueRange<Double>) {
        this.min += range.min
        this.max += range.max
    }

}

internal fun convertToArray(str: String, hasNegative: Boolean = true): Pair<String, String?>? {
    val positivePattern = Pattern.compile("(\\d)+([.]?\\d+)?")
    val negativePattern = Pattern.compile("-?(\\d)+([.]?\\d+)?")
    val matcher = (if (hasNegative) negativePattern else positivePattern).matcher(str)
    val min = if (matcher.find()) matcher.group() else return null
    val max = if (matcher.find()) matcher.group() else null
    return min to max
}

fun convertRangeInt(str: String, hasNegative: Boolean = true): IntValueRange {
    val (min, max) = convertToArray(str, hasNegative) ?: Pair("0", "0")
    return IntValueRange(min.toInt(), max?.toInt() ?: min.toInt())
}

fun convertRangeDouble(str: String, hasNegative: Boolean = true): DoubleValueRange {
    val (min, max) = convertToArray(str, hasNegative) ?: Pair("0.0", "0.0")
    return DoubleValueRange(min.toDouble(), max?.toDouble() ?: min.toDouble())
}
