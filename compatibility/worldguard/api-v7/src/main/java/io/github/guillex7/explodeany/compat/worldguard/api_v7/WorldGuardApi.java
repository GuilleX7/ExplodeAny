package io.github.guillex7.explodeany.compat.worldguard.api_v7;

import java.util.logging.Logger;

import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.DelayedRegionOverlapAssociation;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;

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
        try {
            return WorldGuard.getInstance() != null;
        } catch (final NoClassDefFoundError e) {
            return false;
        }
    }

    @Override
    public void load(final Version bukkitVersion, final Logger logger) {
        this.regionQuery = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

        this.announce(logger);
    }

    private void announce(final Logger logger) {
        logger.info("Loaded support for WorldGuard v7.x");
    }

    @Override
    public IWorldGuardLocationChecker getLocationChecker(final Location sourceLocation) {
        final RegionAssociable originRegionAssociable = new DelayedRegionOverlapAssociation(regionQuery,
                BukkitAdapter.adapt(sourceLocation));

        return (targetLocation) -> this.regionQuery.testBuild(BukkitAdapter.adapt(targetLocation),
                originRegionAssociable,
                Flags.BLOCK_BREAK, Flags.TNT);
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
