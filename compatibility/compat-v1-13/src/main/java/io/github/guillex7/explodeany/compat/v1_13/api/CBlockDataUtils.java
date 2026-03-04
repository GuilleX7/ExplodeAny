package io.github.guillex7.explodeany.compat.v1_13.api;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;

public class CBlockDataUtils extends io.github.guillex7.explodeany.compat.v1_9.api.CBlockDataUtils {
    @Override
    public boolean isBlockWaterlogged(final Block block) {
        final BlockData blockData = block.getBlockData();
        return blockData instanceof Waterlogged && ((Waterlogged) blockData).isWaterlogged();
    }

    @Override
    public void setIsBlockWaterlogged(final Block block, final boolean isWaterlogged) {
        final BlockData blockData = block.getBlockData();
        if (blockData instanceof Waterlogged) {
            ((Waterlogged) blockData).setWaterlogged(isWaterlogged);
        }
        block.setBlockData(blockData);
    }
}
