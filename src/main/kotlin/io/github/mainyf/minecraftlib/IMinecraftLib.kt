package io.github.mainyf.minecraftlib

import io.github.mainyf.minecraftlib.utils.IDebounce
import org.bukkit.command.CommandMap

interface IMinecraftLib {

    /**
     * get command map
     */
    fun getCommandMap(): CommandMap

    /**
     * get debounce tool class
     */
    fun getDebounce(wait: Long): IDebounce

}