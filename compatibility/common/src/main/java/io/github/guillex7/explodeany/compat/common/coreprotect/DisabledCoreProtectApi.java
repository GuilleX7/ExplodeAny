package io.github.guillex7.explodeany.compat.common.coreprotect;

import java.util.logging.Logger;

import org.bukkit.block.Block;

import io.github.guillex7.explodeany.compat.common.Version;

public class DisabledCoreProtectApi implements ICoreProtectApi {
    @Override
    public String getName() {
        return "CoreProtect";
    }

    @Override
    public boolean isEnvironmentSuitable(final Version bukkitVersion) {
        return true;
    }

    @Override
    public void load(final Version bukkitVersion, final Logger logger) {
        // Do nothing
    }

    @Override
    public void logRemoval(final String userName, final Block block) {
        // Not supported
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
