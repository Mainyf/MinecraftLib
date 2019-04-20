@file:Suppress("UNREACHABLE_CODE")

package io.github.mainyf.minecraftlib.data

import io.github.mainyf.minecraftlib.data.storage.JsonDataManager
import io.github.mainyf.minecraftlib.data.storage.YamlDataManager
import org.bukkit.plugin.Plugin

object DataManagerFactory {

    inline fun <reified T : BaseData> getDataManager(plugin: Plugin, storageType: StorageType, dataKey: String): IDataManager<T> {
        return when(storageType) {
            StorageType.YAML -> {
                return YamlDataManager(
                    plugin.dataFolder,
                    dataKey,
                    T::class.java
                )
            }
            StorageType.JSON -> {
                return JsonDataManager(
                    plugin.dataFolder,
                    dataKey,
                    T::class.java
                )
            }
        }
    }

}