@file:Suppress("UNREACHABLE_CODE")

package io.github.mainyf.minecraftlib.data

import io.github.mainyf.minecraftlib.MinecraftLib
import io.github.mainyf.minecraftlib.data.storage.JsonDataManager
import io.github.mainyf.minecraftlib.data.storage.YamlDataManager

object DataManagerFactory {

    inline fun <reified T : BaseData> getDataManager(storageType: StorageType, dataKey: String): IDataManager<T> {
        return when(storageType) {
            StorageType.YAML -> {
                return YamlDataManager(
                    MinecraftLib.INSTANCE!!.dataFolder,
                    dataKey,
                    T::class.java
                )
            }
            StorageType.JSON -> {
                return JsonDataManager(
                    MinecraftLib.INSTANCE!!.dataFolder,
                    dataKey,
                    T::class.java
                )
            }
        }
    }

}