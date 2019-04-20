package io.github.mainyf.minecraftlib.utils

import org.apache.commons.lang3.RandomUtils

object RandomUtils {

    fun randomInt(min: Int, max: Int): Int {
        if (min < 0 && max > 0) {
            val _min = Math.abs(min)
            return RandomUtils.nextInt(_min, max) - _min * 2
        }
        if (min > 0 && max < 0) {
            val _max = Math.abs(max)
            return RandomUtils.nextInt(min, _max) - _max * 2
        }
        if (min < 0 && max < 0) {
            val _min = Math.abs(max)
            val _max = Math.abs(min)
            return -RandomUtils.nextInt(_min, _max)
        }
        return RandomUtils.nextInt(min, max)
    }

    fun randomDouble(min: Double, max: Double): Double {
        if (min < 0 && max > 0) {
            val _min = Math.abs(min)
            return RandomUtils.nextDouble(_min, max) - _min * 2
        }
        if (min > 0 && max < 0) {
            val _max = Math.abs(max)
            return RandomUtils.nextDouble(min, _max) - _max * 2
        }
        if (min < 0 && max < 0) {
            val _min = Math.abs(max)
            val _max = Math.abs(min)
            return -RandomUtils.nextDouble(_min, _max)
        }
        return RandomUtils.nextDouble(min, max)
    }

    fun randomPercent(percent: Double): Boolean {
        return percent > randomInt(0, 100)
    }
}