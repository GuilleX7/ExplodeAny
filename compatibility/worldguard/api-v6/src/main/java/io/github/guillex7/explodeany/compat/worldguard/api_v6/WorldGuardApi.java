package io.github.guillex7.explodeany.compat.worldguard.api_v6;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.protection.DelayedRegionOverlapAssociation;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.worldguard.IWorldGuardApi;
import io.github.guillex7.explodeany.compat.common.worldguard.data.IWorldGuardLocationChecker;

public class WorldGuardApi implements IWorldGuardApi {
    private RegionQuery regionQuery;

    @Override
    public String getName() {
        return "WorldGuard";
    }

    @Override
    public boolean isEnvironmentSuitable(final Version bukkitVersion) {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        try {
            return plugin != null && plugin.isEnabled() && plugin instanceof WorldGuardPlugin;
        } catch (final NoClassDefFoundError e) {
            return false;
        }
    }

    @Override
    public void load(final Version bukkitVersion, final Logger logger) {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        this.regionQuery = ((WorldGuardPlugin) plugin).getRegionContainer().createQuery();

        this.announce(logger);
    }

    private void announce(final Logger logger) {
        logger.info("Loaded support for WorldGuard v6.x");
    }

    @Override
    public IWorldGuardLocationChecker getLocationChecker(final Location sourceLocation) {
        final RegionAssociable originRegionAssociable = new DelayedRegionOverlapAssociation(this.regionQuery,
                sourceLocation);

        return (targetLocation) -> this.regionQuery.testBuild(targetLocation, originRegionAssociable,
                DefaultFlag.BLOCK_BREAK, DefaultFlag.TNT);
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
