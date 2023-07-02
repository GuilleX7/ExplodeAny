package io.github.guillex7.explodeany.explosion.liquid;

import org.bukkit.Location;
import org.bukkit.block.Block;

import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;

public class BlockLiquidDetector {
    public boolean isBlockLiquidlike(Location blockLocation) {
        return isBlockLiquidlike(blockLocation.getBlock());
    }

    public boolean isBlockLiquidlike(Block block) {
        return block.isLiquid() || CompatibilityManager.getInstance().getApi().getBlockDataUtils()
                .isBlockWaterlogged(block);
    }
}
