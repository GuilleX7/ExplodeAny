package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.entity.EntityType;

public interface IExplosionUtils {
    double getExplosionRadiusAndPower(EntityType entityType, boolean isCharged);
}
