@file:Suppress("NOTHING_TO_INLINE")

package io.github.mainyf.minecraftlib.extensions

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

fun ItemStack.setDisplayName(displayName: String): ItemStack? {
    return this.also {
        it.itemMeta = it.itemMeta.also { item ->
            item.displayName = displayName
        }
    }
}

fun ItemStack.getLore(): MutableList<String> {
    if (type == Material.AIR) {
        return mutableListOf()
    }
    val itemMeta = itemMeta
    if (itemMeta == null || !itemMeta.hasLore()) {
        return mutableListOf()
    }
    return itemMeta.lore ?: mutableListOf()
}

fun ItemStack.setLore(lore: List<String>): ItemStack? {
    return this.also {
        it.itemMeta = it.itemMeta.also { item ->
            item.lore = lore
        }
    }
}

fun ItemStack.hasLore(str: String): Boolean {
    val lore = itemMeta.lore ?: return false
    return lore.contains(str)
}

fun ItemStack.addEnchantMeta(enchantment: Enchantment, level: Int, ignoreRestrictions: Boolean): Boolean {
    val itemMeta = itemMeta ?: return false
    val result = itemMeta.addEnchant(enchantment, level, ignoreRestrictions)
    setItemMeta(itemMeta)
    return result
}

