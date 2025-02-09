package io.github.guillex7.explodeany.explosion.liquid;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;

public class BlockLiquidDetector {
    public static boolean isLocationLiquidlike(Location blockLocation) {
        return BlockLiquidDetector.isBlockLiquidlike(blockLocation.getBlock());
    }

    public static boolean isBlockLiquidlike(Block block) {
        return block.isLiquid() || CompatibilityManager.getInstance().getApi().getBlockDataUtils()
                .isBlockWaterlogged(block);
    }

    public static boolean isLocationSurroundedByLiquid(Location blockLocation) {
        return BlockLiquidDetector.isBlockSurroundedByLiquid(blockLocation.getBlock());
    }

    public static boolean isBlockSurroundedByLiquid(Block block) {
        return block.getRelative(BlockFace.UP).isLiquid()
                || block.getRelative(BlockFace.NORTH).isLiquid()
                || block.getRelative(BlockFace.SOUTH).isLiquid()
                || block.getRelative(BlockFace.EAST).isLiquid()
                || block.getRelative(BlockFace.WEST).isLiquid();
    }
}
