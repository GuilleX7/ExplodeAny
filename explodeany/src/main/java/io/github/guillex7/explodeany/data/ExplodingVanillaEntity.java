package io.github.guillex7.explodeany.data;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;

public enum ExplodingVanillaEntity {
    WITHER("WITHER", 7d),
    ENDER_CRYSTAL("ENDER_CRYSTAL", 6d),
    PRIMED_TNT("PRIMED_TNT", 4d),
    MINECART_TNT("MINECART_TNT", 4d),
    CREEPER("CREEPER", 3d),
    CHARGED_CREEPER("CHARGED_CREEPER", 5d),
    FIREBALL("FIREBALL", 1d),
    DRAGON_FIREBALL("DRAGON_FIREBALL", 1d),
    SMALL_FIREBALL("SMALL_FIREBALL", 1d),
    WITHER_SKULL("WITHER_SKULL", 1d),
    CHARGED_WITHER_SKULL("CHARGED_WITHER_SKULL", 1d),
    BED("BED", 5.0),
    RESPAWN_ANCHOR("RESPAWN_ANCHOR", 5.0);

    private final String name;
    private final double explosionRadius;

    public static boolean isEntityNameValid(String entityName) {
        return ExplodingVanillaEntity.fromEntityTypeName(entityName) != null;
    }

    public static ExplodingVanillaEntity fromEntityTypeName(String entityTypeName) {
        String uppercasedEntityTypeName = entityTypeName.toUpperCase();

        // Hint: some entities were renamed after Minecraft 1.21
        if ("TNT".equals(uppercasedEntityTypeName)) {
            return ExplodingVanillaEntity.PRIMED_TNT;
        } else if ("TNT_MINECART".equals(uppercasedEntityTypeName)) {
            return ExplodingVanillaEntity.MINECART_TNT;
        } else if ("END_CRYSTAL".equals(uppercasedEntityTypeName)) {
            return ExplodingVanillaEntity.ENDER_CRYSTAL;
        } else {
            try {
                return ExplodingVanillaEntity.valueOf(uppercasedEntityTypeName);
            } catch (IllegalArgumentException | NullPointerException e) {
                return null;
            }
        }
    }

    public static ExplodingVanillaEntity fromEntity(Entity entity) {
        String entityTypeName = entity.getType().toString();
        EntityType entityType = entity.getType();

        if (entityType.equals(EntityType.CREEPER) && ((Creeper) entity).isPowered()
                || entityType.equals(EntityType.WITHER_SKULL) && ((WitherSkull) entity).isCharged()) {
            entityTypeName = "CHARGED_".concat(entityTypeName);
        }

        return ExplodingVanillaEntity.fromEntityTypeName(entityTypeName);
    }

    private ExplodingVanillaEntity(String name, double explosionRadius) {
        this.name = name;
        this.explosionRadius = explosionRadius;
    }

    public String getName() {
        return name;
    }

    public double getExplosionRadius() {
        return explosionRadius;
    }
}
