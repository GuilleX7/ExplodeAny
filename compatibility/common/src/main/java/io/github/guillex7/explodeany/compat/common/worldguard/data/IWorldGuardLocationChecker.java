package io.github.guillex7.explodeany.compat.common.worldguard.data;

import org.bukkit.Location;

public interface IWorldGuardLocationChecker {
    boolean canBreakAtLocation(Location targetLocation);
}
