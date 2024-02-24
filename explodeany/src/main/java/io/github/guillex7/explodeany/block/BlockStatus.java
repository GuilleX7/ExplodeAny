package io.github.guillex7.explodeany.block;

import org.bukkit.Material;
import org.bukkit.block.Block;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class BlockStatus {
    private Material material;
    private double durability;

    public static double getDefaultBlockDurability() {
        return ConfigurationManager.getInstance().getBlockDurability();
    }

    public static BlockStatus defaultForBlock(Block block) {
        return new BlockStatus(block.getType(), getDefaultBlockDurability());
    }

    public BlockStatus(Material material, double durability) {
        this.material = material;
        this.durability = durability;
    }

    public Material getMaterial() {
        return material;
    }

    public double getDurability() {
        return durability;
    }

    public boolean isCongruentWith(Block block) {
        return block.getType().equals(this.material);
    }

    public boolean damage(double damage) {
        this.durability -= damage;
        return this.shouldBreak();
    }

    public boolean shouldBreak() {
        return this.durability <= 0.0;
    }

    public void sanitize() {
        this.durability = MathUtils.ensureMax(this.durability, BlockStatus.getDefaultBlockDurability());
    }
}
