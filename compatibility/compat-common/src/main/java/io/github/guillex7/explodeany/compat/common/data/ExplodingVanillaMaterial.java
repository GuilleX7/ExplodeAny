package io.github.guillex7.explodeany.compat.common.data;

import org.bukkit.Material;
import org.bukkit.block.Block;

public enum ExplodingVanillaMaterial {
    BED("BED", 5.0),
    RESPAWN_ANCHOR("RESPAWN_ANCHOR", 5.0);

    private final String name;
    private final double explosionRadius;

    public static boolean isMaterialNameValid(String materialName) {
        return ExplodingVanillaMaterial.fromMaterialName(materialName) != null;
    }

    public static ExplodingVanillaMaterial fromBlock(Block block) {
        return ExplodingVanillaMaterial.fromMaterial(block.getType());
    }

    public static ExplodingVanillaMaterial fromMaterial(Material material) {
        return ExplodingVanillaMaterial.fromMaterialName(material.toString());
    }

    public static ExplodingVanillaMaterial fromMaterialName(String materialName) {
        try {
            return ExplodingVanillaMaterial.valueOf(materialName);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    private ExplodingVanillaMaterial(String materialName, double explosionRadius) {
        this.name = materialName;
        this.explosionRadius = explosionRadius;
    }

    public String getName() {
        return this.name;
    }

    public double getExplosionRadius() {
        return this.explosionRadius;
    }
}
