package io.github.guillex7.explodeany.block;

import org.bukkit.Material;
import org.bukkit.block.Block;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.section.MaterialConfiguration;
import io.github.guillex7.explodeany.util.MathUtils;

public class BlockStatus {
    private final Material material;
    private double durability;
    private long lastDamaged;

    // Hint: not really part of this class, but cached for performance
    private final double maximumDurability;
    private final MaterialConfiguration materialConfiguration;

    public static BlockStatus defaultForBlock(final Block block) {
        return new BlockStatus(block.getType(), ConfigurationManager.getInstance().getGlobalBlockDurability(), -1);
    }

    public static BlockStatus of(final Material material, final double durability, final long lastDamaged) {
        return new BlockStatus(material, durability, lastDamaged);
    }

    private BlockStatus(final Material material, final double durability, final long lastDamaged) {
        this.material = material;
        this.durability = durability;
        this.lastDamaged = lastDamaged;

        this.maximumDurability = ConfigurationManager.getInstance().getGlobalBlockDurability();
        this.materialConfiguration = ConfigurationManager.getInstance().getMaterialConfiguration(material);
    }

    public Material getMaterial() {
        return this.material;
    }

    public double getRawDurability() {
        return this.durability;
    }

    public double getDurability(final long currentTime) {
        double currentDurability = this.durability;

        if (this.lastDamaged != -1) {
            final long elapsedTimeForRegeneration = currentTime - this.lastDamaged
                    - this.materialConfiguration.getDelayBeforeRegeneration().asMilliseconds();
            if (elapsedTimeForRegeneration > 0) {
                currentDurability = Math.min(
                        this.durability + elapsedTimeForRegeneration
                                * this.materialConfiguration.getDurabilityRegenerationPerMillisecond(),
                        this.maximumDurability);
            }
        }

        return currentDurability;
    }

    public double getMaximumDurability() {
        return this.maximumDurability;
    }

    public long getLastDamaged() {
        return this.lastDamaged;
    }

    public MaterialConfiguration getMaterialConfiguration() {
        return this.materialConfiguration;
    }

    public boolean isCongruentWith(final Block block) {
        return block.getType().equals(this.material);
    }

    public boolean damage(final double damage, final long currentTime) {
        this.durability = this.getDurability(currentTime) - damage;
        this.lastDamaged = currentTime;
        return this.shouldBreak();
    }

    public boolean shouldBreak() {
        return this.durability <= 0.0;
    }

    public void sanitize() {
        this.durability = MathUtils.ensureMax(this.durability, this.maximumDurability);
    }
}
