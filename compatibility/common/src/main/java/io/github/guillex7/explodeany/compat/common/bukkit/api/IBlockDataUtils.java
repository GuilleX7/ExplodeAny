package io.github.guillex7.explodeany.compat.common.bukkit.api;

import org.bukkit.block.Block;

public interface IBlockDataUtils {
    boolean isBlockWaterlogged(Block block);

    void setIsBlockWaterlogged(Block block, boolean isWaterlogged);
}
