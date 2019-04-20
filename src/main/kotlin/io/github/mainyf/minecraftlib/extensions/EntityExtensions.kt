package io.github.mainyf.minecraftlib.extensions

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile

fun Entity.getEntityName(): String {
    return if(this is Player) this.name else this.type.name.replace("_", " ").toLowerCase()
}

fun Entity.getOriginalDamager(): Entity {
    return if(this is Projectile) this.shooter else this
}