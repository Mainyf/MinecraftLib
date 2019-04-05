package io.github.mainyf.minecraftlib

import io.github.mainyf.minecraftlib.utils.IDebounce
import org.bukkit.command.CommandMap
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

class MinecraftLib : JavaPlugin(), IMinecraftLib {

    companion object {
        var INSTANCE: MinecraftLib? = null
    }

    private var commandMap: CommandMap? = null

    override fun onEnable() {
        INSTANCE = this

        server.servicesManager.register(IMinecraftLib::class.java, this, this, ServicePriority.Normal)
    }

    override fun getCommandMap(): CommandMap {
        if(commandMap == null) {
            commandMap = server.javaClass.getMethod("getCommandMap").invoke(server) as CommandMap
        }
        return commandMap!!
    }

    override fun getDebounce(wait: Long): IDebounce {
        return Debounce(wait)
    }

}
