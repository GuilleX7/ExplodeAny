package io.github.guillex7.explodeany.compat.common.coreprotect;

import org.bukkit.block.Block;

import io.github.guillex7.explodeany.compat.common.LoadableApi;

public interface ICoreProtectApi extends LoadableApi {
    void logRemoval(String userName, Block block);
}