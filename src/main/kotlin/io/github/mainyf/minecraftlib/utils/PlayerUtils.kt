package io.github.mainyf.minecraftlib.utils

import io.github.mainyf.minecraftlib.extensions.reduce
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

object PlayerUtils {

    fun getPlayerMarkRangeValue(entity: LivingEntity, mark: String): Double? {
        val equipment = entity.equipment
        return getPlayerMarkRangeValue(listOf(
            equipment.itemInHand,
            *equipment.armorContents
        ), mark)
    }

    fun getPlayerMarkRangeValue(items: Collection<ItemStack>, mark: String): Double? {
        return items.reduce({ prev: Double?, item: ItemStack, i: Int ->
            val range = LoreUtils.getItemRangeValue(item, mark) ?: return@reduce prev
            val value = range.getRandomValue()
            if(prev == null) value else prev + value
        })
    }

    fun getPlayerMarkRange(entity: LivingEntity, mark: String): DoubleValueRange? {
        val equipment = entity.equipment
        return getPlayerMarkRange(listOf(
            equipment.itemInHand,
            *equipment.armorContents
        ), mark)
    }

    fun getPlayerMarkRange(items: Collection<ItemStack>, mark: String): DoubleValueRange? {
        return items.reduce({ prev: DoubleValueRange?, item: ItemStack, i: Int ->
            val range = LoreUtils.getItemRangeValue(item, mark) ?: return@reduce prev
            if(prev == null) range else {
                prev.mergeRange(range)
                prev
            }
        })
    }

    fun getPlayerMarkValue(entity: LivingEntity, mark: String): Double? {
        val equipment = entity.equipment
        return getPlayerMarkValue(listOf(
            equipment.itemInHand,
            *equipment.armorContents
        ), mark)
    }

    fun getPlayerMarkValue(items: Collection<ItemStack>, mark: String): Double? {
        return items.reduce({ prev: Double?, item: ItemStack, i: Int ->
            val value = LoreUtils.getItemValue(item, mark) ?: return@reduce prev
            if(prev == null) value else prev + value
        })
    }

    fun getPlayerMarkPercentValue(entity: LivingEntity, mark: String): Double? {
        val equipment = entity.equipment
        return getPlayerMarkPercentValue(listOf(
            equipment.itemInHand,
            *equipment.armorContents
        ), mark)
    }

    fun getPlayerMarkPercentValue(items: Collection<ItemStack>, mark: String): Double? {
        return items.reduce({ prev: Double?, item: ItemStack, i: Int ->
            val value = LoreUtils.getItemPercent(item, mark) ?: return@reduce prev
            if(prev == null) value else prev + value
        })
    }

}