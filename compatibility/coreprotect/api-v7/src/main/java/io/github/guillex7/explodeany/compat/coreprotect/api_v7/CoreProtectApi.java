package io.github.guillex7.explodeany.compat.coreprotect.api_v7;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.coreprotect.ICoreProtectApi;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class CoreProtectApi implements ICoreProtectApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 13);

    private CoreProtectAPI coreProtectApi;

    private Version getMinimumSupportedBukkitVersion() {
        return this.minimumSupportedBukkitVersion;
    }

    @Override
    public String getName() {
        return "CoreProtect";
    }

    @Override
    public boolean isEnvironmentSuitable(final Version bukkitVersion) {
        if (bukkitVersion.isBefore(this.getMinimumSupportedBukkitVersion())) {
            return false;
        }

        final Plugin plugin = Bukkit.getPluginManager().getPlugin("CoreProtect");
        try {
            if (plugin == null || !plugin.isEnabled() || !(plugin instanceof CoreProtect)) {
                return false;
            }

            final CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
            return CoreProtect.APIVersion() >= 7;
        } catch (final NoClassDefFoundError e) {
            return false;
        }
    }

    @Override
    public void load(final Version bukkitVersion, final Logger logger) {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("CoreProtect");
        this.coreProtectApi = ((CoreProtect) plugin).getAPI();

        this.announce(logger);
    }

    protected void announce(final Logger logger) {
        logger.info("Loaded support for CoreProtect v19+");
    }

    @Override
    public void logRemoval(final String userName, final Block block) {
        coreProtectApi.logRemoval(userName, block.getLocation(), block.getType(), block.getBlockData());
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
