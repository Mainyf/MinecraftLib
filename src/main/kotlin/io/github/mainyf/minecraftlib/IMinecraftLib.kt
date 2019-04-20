package io.github.mainyf.minecraftlib

import org.bukkit.command.CommandMap

interface IMinecraftLib {

    /**
     * get command map
     */
    fun getCommandMap(): CommandMap

    /**
     * get throttle tool class
     */
    fun getThrottle(wait: Double): Throttle

}