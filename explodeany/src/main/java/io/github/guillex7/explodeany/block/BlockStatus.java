package io.github.guillex7.explodeany.block;

import org.bukkit.Material;
import org.bukkit.block.Block;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class BlockStatus {
    private Material material;
    private double durability;

    public static BlockStatus of(Material material, double durability) {
        return new BlockStatus(material, durability);
    }

    public static BlockStatus defaultForBlock(Block block) {
        return BlockStatus.of(block.getType(), getDefaultBlockDurability());
    }

    public static double getDefaultBlockDurability() {
        return ConfigurationManager.getInstance().getBlockDurability();
    }

    private BlockStatus(Material material, double durability) {
        super();
        this.material = material;
        this.durability = durability;
    }

    public boolean isCongruentWith(Block block) {
        return block.getType().equals(getMaterial());
    }

    public boolean damage(double damage) {
        setDurability(getDurability() - damage);
        return shouldBreak();
    }

    public boolean shouldBreak() {
        return getDurability() <= 0.0;
    }

    public void sanitize() {
        setDurability(MathUtils.ensureMax(getDurability(), BlockStatus.getDefaultBlockDurability()));
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getDurability() {
        return durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(durability);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((material == null) ? 0 : material.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlockStatus other = (BlockStatus) obj;
        if (Double.doubleToLongBits(durability) != Double.doubleToLongBits(other.durability))
            return false;
        if (material != other.material)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BlockStatus [material=" + material + ", durability=" + durability + "]";
    }
}
