package io.github.mainyf.minecraftlib.utils

import java.util.*

interface IDebounce {

    var wait: Long

    fun process(uuid: UUID, success: () -> Unit, not: (surplus: Long) -> Unit)

}