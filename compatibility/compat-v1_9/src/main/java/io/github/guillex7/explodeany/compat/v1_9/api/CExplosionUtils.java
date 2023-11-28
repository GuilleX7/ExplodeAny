package io.github.guillex7.explodeany.compat.v1_9.api;

import org.bukkit.entity.EntityType;

public class CExplosionUtils extends io.github.guillex7.explodeany.compat.v1_8.api.CExplosionUtils {
    @Override
    public double getExplosionRadiusAndPower(EntityType entityType, boolean isCharged) {
        switch (entityType) {
            case DRAGON_FIREBALL: // Dragon fireball
                return 1d;
            default:
                return super.getExplosionRadiusAndPower(entityType, isCharged);
        }
    }
}
