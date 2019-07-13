package io.github.mainyf.minecraftlib.extensions

fun <T> List<T>.getWithFirst(predicate: (T) -> Boolean): T? {
    for (item in this) {
        if (predicate(item))
            return item
    }
    return null
}

fun <T> List<T>.getWith(predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        if (predicate(item))
            result.add(item)
    }
    return result
}

fun <T> Set<T>.getWithFirst(predicate: (T) -> Boolean): T? {
    for (item in this) {
        if (predicate(item))
            return item
    }
    return null
}

fun <T> Set<T>.getWith(predicate: (T) -> Boolean): Set<T> {
    val result = mutableSetOf<T>()
    for (item in this) {
        if (predicate(item))
            result.add(item)
    }
    return result
}

inline fun <reified T> Array<T>.getWith(predicate: (T) -> Boolean): Array<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        if (predicate(item))
            result.add(item)
    }
    return result.toTypedArray()
}

fun <T> Array<T>.getWithFirst(predicate: (T) -> Boolean): T? {
    for (item in this) {
        if (predicate(item))
            return item
    }
    return null
}

fun <T> MutableList<T>.setWithFirst(predicate: (T) -> Boolean, element: T) {
    val iterator = listIterator()
    while (iterator.hasNext()) {
        if (predicate(iterator.next())) {
            iterator.set(element)
            return
        }
    }
}

fun <T> MutableList<T>.setWith(predicate: (T) -> Boolean, element: T) {
    val iterator = listIterator()
    while (iterator.hasNext()) {
        if (predicate(iterator.next())) {
            iterator.set(element)
        }
    }
}

fun <T> MutableList<T>.getMaxNumber(predicate: (T) -> Number): Long {
    var i = 0L
    val iterator = listIterator()
    while (iterator.hasNext()) {
        val tmp = predicate(iterator.next()).toLong()
        if (tmp > i) {
            i = tmp
        }
    }
    return i
}

fun <T> Array<T>.containsWith(predicate: (T) -> Boolean): Boolean {
    for (item in this) {
        if (predicate(item))
            return true
    }
    return false
}

fun <T> Collection<T>.containsWith(predicate: (T) -> Boolean): Boolean {
    for (item in this) {
        if (predicate(item))
            return true
    }
    return false
}

fun <T> List<T>.pagination(pageIndex: Int, pageSize: Int): MutableList<T> {
    val result = mutableListOf<T>()
    if (pageIndex <= 0) {
        throw IllegalArgumentException("pageIndex least 1")
    }
    if (isEmpty()) {
        return result
    }
    if (size < pageSize) {
        return this.toMutableList()
    }
    val currentFirst = (pageIndex - 1) * pageSize
    if (currentFirst >= size) {
        throw IllegalArgumentException("pageIndex max: ${size / pageSize}")
    }
    for (i in currentFirst..size) {
        if (result.size == pageSize) {
            break
        }
        result.add(get(i))
    }
    return result
}

fun <T> List<T>.getPageCount(pageSize: Int): Int {
    val result = size / pageSize
    return if (result < 1) 1 else result
}

fun <K, V> Map<K, V>.getValueByKeyWith(predicate: (K) -> Boolean): V? {
    forEach {
        if (predicate.invoke(it.key)) {
            return it.value
        }
    }
    return null
}

fun <K, V> Map<K, V>.mergeMap(map: Map<K, V>): Map<K, V> {
    val result = this.toMutableMap()
    result.putAll(map)
    return result
}

fun <K, V> Map<K, V>.mergeMap(vararg maps: Map<K, V>): Map<K, V> {
    val result = this.toMutableMap()
    for (m in maps) {
        result.putAll(m)
    }
    return result
}

fun <K, V, NK, NV> Map<K, V>.mapReplace(fn: (value: Map.Entry<K, V>) -> Pair<NK, NV>): Map<NK, NV> {
    val result = mutableMapOf<NK, NV>()
    forEach {
        val newPair = fn.invoke(it)
        result[newPair.first] = newPair.second
    }
    return result
}

fun <T, R> Collection<T>.reduce(fn: (prev: R?, curr: T, index: Int) -> R, initialValue: R? = null): R? {
    var result = initialValue
    for ((index, v) in this.withIndex()) {
        result = fn.invoke(result, v, index)
    }
    return result
}

fun <T, R> Array<T>.reduce(fn: (prev: R?, curr: T, index: Int) -> R, initialValue: R? = null): R? {
    var result = initialValue
    for ((index, v) in this.withIndex()) {
        result = fn.invoke(result, v, index)
    }
    return result
}