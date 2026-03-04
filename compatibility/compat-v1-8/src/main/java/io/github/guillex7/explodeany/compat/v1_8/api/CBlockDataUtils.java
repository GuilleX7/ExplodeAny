package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.block.Block;

import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;

public class CBlockDataUtils implements IBlockDataUtils {
    @Override
    public boolean isBlockWaterlogged(final Block block) {
        // Not supported
        return false;
    }

    @Override
    public void setIsBlockWaterlogged(final Block block, final boolean isWaterlogged) {
        // Not supported
    }
}
