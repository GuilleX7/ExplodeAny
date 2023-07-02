package io.github.guillex7.explodeany.compat.v1_13.api;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;

import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;

public class CBlockDataUtils implements IBlockDataUtils {
    @Override
    public boolean isBlockWaterlogged(Block block) {
        BlockData blockData = block.getBlockData();
        return blockData instanceof Waterlogged && ((Waterlogged) blockData).isWaterlogged();
    }

    @Override
    public void setIsBlockWaterlogged(Block block, boolean isWaterlogged) {
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Waterlogged) {
            ((Waterlogged) blockData).setWaterlogged(isWaterlogged);
        }
        block.setBlockData(blockData);
    }
}
