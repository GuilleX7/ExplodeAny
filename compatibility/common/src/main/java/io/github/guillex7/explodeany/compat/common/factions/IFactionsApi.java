package io.github.guillex7.explodeany.compat.common.factions;

import org.bukkit.Location;

import io.github.guillex7.explodeany.compat.common.LoadableApi;

public interface IFactionsApi extends LoadableApi {
    boolean canBreakAtLocation(Location targetLocation);
}
