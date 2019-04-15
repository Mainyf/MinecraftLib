package io.github.mainyf.minecraftlib.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class LivingEntityUtils {

    public static double getHealth(Entity entity) {
        if(!(entity instanceof LivingEntity)) {
            throw new RuntimeException("entity not living entity");
        }
        return ((LivingEntity)entity).getHealth();
    }

    public static void setHealth(Entity entity, double health) {
        if(!(entity instanceof LivingEntity)) {
            throw new RuntimeException("entity not living entity");
        }
        ((LivingEntity)entity).setHealth(health);
    }

}
