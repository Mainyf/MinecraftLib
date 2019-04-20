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
            val range = LoreUtils.getItemRangeValueDouble(item, mark) ?: return@reduce prev
            val value = range.randomValue()
            if(prev == null) value else prev + value
        })
    }

    fun getPlayerMarkRangeDouble(entity: LivingEntity, mark: String): DoubleValueRange? {
        val equipment = entity.equipment
        return getPlayerMarkRangeDouble(listOf(
            equipment.itemInHand,
            *equipment.armorContents
        ), mark)
    }

    fun getPlayerMarkRangeDouble(items: Collection<ItemStack>, mark: String): DoubleValueRange? {
        return items.reduce({ prev: DoubleValueRange?, item: ItemStack, i: Int ->
            val range = LoreUtils.getItemRangeValueDouble(item, mark) ?: return@reduce prev
            if(prev == null) range else {
                prev.mergeRange(range)
                prev
            }
        })
    }

    fun getPlayerMarkRangeInt(entity: LivingEntity, mark: String): IntValueRange? {
        val equipment = entity.equipment
        return getPlayerMarkRangeInt(listOf(
            equipment.itemInHand,
            *equipment.armorContents
        ), mark)
    }

    fun getPlayerMarkRangeInt(items: Collection<ItemStack>, mark: String): IntValueRange? {
        return items.reduce({ prev: IntValueRange?, item: ItemStack, i: Int ->
            val range = LoreUtils.getItemRangeValueInt(item, mark) ?: return@reduce prev
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