package io.github.mainyf.minecraftlib

import java.util.*

class Throttle(var wait: Double) {

    private val storeMap = mutableMapOf<UUID, Double>()

    fun process(uuid: UUID, success: () -> Unit, not: (surplus: Double) -> Unit) {
        if (storeMap.containsKey(uuid)) {
            val prevTime = storeMap[uuid]!!
            val pastTime = System.currentTimeMillis() - prevTime
            if (pastTime > wait) {
                success.invoke()
                storeMap[uuid] = System.currentTimeMillis().toDouble()
            } else {
                not.invoke(wait - pastTime)
            }
        } else {
            success.invoke()
            storeMap[uuid] = System.currentTimeMillis().toDouble()
        }
    }

}
