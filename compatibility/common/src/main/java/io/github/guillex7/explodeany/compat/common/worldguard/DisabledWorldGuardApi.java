package io.github.guillex7.explodeany.compat.common.worldguard;

import java.util.logging.Logger;

import org.bukkit.Location;

import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.worldguard.data.IWorldGuardLocationChecker;

public class DisabledWorldGuardApi implements IWorldGuardApi {
    @Override
    public String getName() {
        return "WorldGuard";
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
    public IWorldGuardLocationChecker getLocationChecker(final Location sourceLocation) {
        return (Location location) -> true;
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
