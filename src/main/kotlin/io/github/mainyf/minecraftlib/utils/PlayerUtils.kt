package io.github.mainyf.minecraftlib.utils

import io.github.mainyf.minecraftlib.extensions.reduce
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

object PlayerUtils {
    fun getPlayerMarkRangeValue(entity: LivingEntity, mark: String): Double? {
        val equipment = entity.equipment
        return arrayOf(
            equipment.itemInHand,
            *equipment.armorContents
        ).reduce({ prev: Double?, item: ItemStack, i: Int ->
            val range = LoreUtils.getItemRangeValue(item, mark) ?: return@reduce prev
            val value = range.getRandomValue()
            if(prev == null) value else prev + value
        })
    }

    fun getPlayerMarkValue(entity: LivingEntity, mark: String): Double? {
        val equipment = entity.equipment
        return arrayOf(
            equipment.itemInHand,
            *equipment.armorContents
        ).reduce({ prev: Double?, item: ItemStack, i: Int ->
            val value = LoreUtils.getItemValue(item, mark) ?: return@reduce prev
            if(prev == null) value else prev + value
        })
    }

    fun getPlayerMarkPercentValue(entity: LivingEntity, mark: String): Double? {
        val equipment = entity.equipment
        return arrayOf(
            equipment.itemInHand,
            *equipment.armorContents
        ).reduce({ prev: Double?, item: ItemStack, i: Int ->
            val value = LoreUtils.getItemPercent(item, mark) ?: return@reduce prev
            if(prev == null) value else prev + value
        })
    }

}