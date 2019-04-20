package io.github.mainyf.minecraftlib.extensions

fun <T, K, V> List<T>.toMap(expression: (value: T) -> Pair<K, V>): Map<K, V> {
    val result = mutableMapOf<K, V>()
    forEach {
        val pair = expression.invoke(it)
        result[pair.first] = pair.second
    }
    return result
}

fun <T> List<T>.toSet(expression: (value: T) -> T): Set<T> {
    val result = mutableSetOf<T>()
    forEach { result.add(expression.invoke(it)) }
    return result
}