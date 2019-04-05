package io.github.mainyf.minecraftlib

import io.github.mainyf.minecraftlib.utils.IDebounce
import java.util.*

class Debounce(override var wait: Long) : IDebounce {

    private val storeMap = mutableMapOf<UUID, Long>()

    override fun process(uuid: UUID, success: () -> Unit, not: (surplus: Long) -> Unit) {
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
