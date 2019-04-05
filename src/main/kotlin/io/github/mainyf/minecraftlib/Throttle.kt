package io.github.mainyf.minecraftlib

import java.util.*

class Throttle(var wait: Long) {

    private val storeMap = mutableMapOf<UUID, Long>()

    fun process(uuid: UUID, success: () -> Unit, not: (surplus: Long) -> Unit) {
        if (storeMap.containsKey(uuid)) {
            val prevTime = storeMap[uuid]!!
            val pastTime = System.currentTimeMillis() - prevTime
            if (pastTime > wait) {
                success.invoke()
                storeMap[uuid] = System.currentTimeMillis()
            } else {
                not.invoke(wait - pastTime)
            }
        } else {
            success.invoke()
            storeMap[uuid] = System.currentTimeMillis()
        }
    }

}
