package io.github.guillex7.explodeany.data;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;

public enum ExplodingVanillaEntity {
    WITHER("WITHER", 7d, "#wither"),
    ENDER_CRYSTAL("ENDER_CRYSTAL", 6d, "#ender_crystal"),
    PRIMED_TNT("PRIMED_TNT", 4d, "#tnt"),
    MINECART_TNT("MINECART_TNT", 4d, "#minecart_tnt"),
    CREEPER("CREEPER", 3d, "#creeper"),
    CHARGED_CREEPER("CHARGED_CREEPER", 5d, "#creeper"),
    FIREBALL("FIREBALL", 1d, "#fireball"),
    DRAGON_FIREBALL("DRAGON_FIREBALL", 1d, "#dragon_fireball"),
    SMALL_FIREBALL("SMALL_FIREBALL", 1d, "#small_fireball"),
    WITHER_SKULL("WITHER_SKULL", 1d, "#wither_skull"),
    CHARGED_WITHER_SKULL("CHARGED_WITHER_SKULL", 1d, "#wither_skull"),
    BED("BED", 5.0, "#bed"),
    RESPAWN_ANCHOR("RESPAWN_ANCHOR", 5.0, "#respawn_anchor");

    private final String name;
    private final double explosionRadius;
    private final String coreProtectIdentifier;

    public static boolean isEntityNameValid(final String entityName) {
        return ExplodingVanillaEntity.fromEntityTypeName(entityName) != null;
    }

    public static ExplodingVanillaEntity fromEntityTypeName(final String entityTypeName) {
        final String uppercasedEntityTypeName = entityTypeName.toUpperCase();

        switch (uppercasedEntityTypeName) {
            case "TNT":
                return ExplodingVanillaEntity.PRIMED_TNT;
            case "TNT_MINECART":
                return ExplodingVanillaEntity.MINECART_TNT;
            case "END_CRYSTAL":
                return ExplodingVanillaEntity.ENDER_CRYSTAL;
            default:
                try {
                    return ExplodingVanillaEntity.valueOf(uppercasedEntityTypeName);
                } catch (IllegalArgumentException | NullPointerException e) {
                    return null;
                }
        }
    }

    public static ExplodingVanillaEntity fromEntity(final Entity entity) {
        String entityTypeName = entity.getType().toString();
        final EntityType entityType = entity.getType();

        if (EntityType.CREEPER.equals(entityType) && ((Creeper) entity).isPowered()
                || EntityType.WITHER_SKULL.equals(entityType) && ((WitherSkull) entity).isCharged()) {
            entityTypeName = "CHARGED_".concat(entityTypeName);
        }

        return ExplodingVanillaEntity.fromEntityTypeName(entityTypeName);
    }

    ExplodingVanillaEntity(final String name, final double explosionRadius, final String coreProtectIdentifier) {
        this.name = name;
        this.explosionRadius = explosionRadius;
        this.coreProtectIdentifier = coreProtectIdentifier;
    }

    public String getName() {
        return this.name;
    }

    public double getExplosionRadius() {
        return this.explosionRadius;
    }

    public String getCoreProtectIdentifier() {
        return this.coreProtectIdentifier;
    }
}
