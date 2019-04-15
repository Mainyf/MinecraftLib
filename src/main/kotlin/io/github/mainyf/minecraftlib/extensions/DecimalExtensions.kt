package io.github.mainyf.minecraftlib.extensions

import java.math.BigDecimal

fun Double.convertDecimal(length: Int): Double {
    return BigDecimal(this).setScale(length, BigDecimal.ROUND_HALF_UP).toDouble()
}

fun Float.convertDecimal(length: Int): Double {
    return BigDecimal(this.toDouble()).setScale(length, BigDecimal.ROUND_HALF_UP).toDouble()
}

fun Double.add(double: Double): Double {
    return toBigDecimal().add(double.toBigDecimal()).toDouble()
}

fun Double.multiply(double: Double): Double {
    return toBigDecimal().multiply(double.toBigDecimal()).toDouble()
}

fun Double.divide(double: Double): Double {
    return toBigDecimal().divide(double.toBigDecimal()).toDouble()
}

fun Double.subtract(double: Double): Double {
    return toBigDecimal().subtract(double.toBigDecimal()).toDouble()
}