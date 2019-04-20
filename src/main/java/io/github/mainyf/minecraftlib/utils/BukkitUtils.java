package io.github.mainyf.minecraftlib.utils;

import org.bukkit.Bukkit;

public class BukkitUtils {

    public static Object[] getOnlinePlayers() {
        Object result = Bukkit.getServer().getOnlinePlayers();
        return (Object[]) result;
    }

}
