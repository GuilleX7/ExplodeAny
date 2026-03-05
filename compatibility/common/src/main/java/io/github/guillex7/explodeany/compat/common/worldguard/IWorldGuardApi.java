package io.github.guillex7.explodeany.compat.common.worldguard;

import org.bukkit.Location;

import io.github.guillex7.explodeany.compat.common.LoadableApi;
import io.github.guillex7.explodeany.compat.common.worldguard.data.IWorldGuardLocationChecker;

public interface IWorldGuardApi extends LoadableApi {
    IWorldGuardLocationChecker getLocationChecker(Location sourceLocation);
}
