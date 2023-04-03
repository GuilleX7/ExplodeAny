package io.github.guillex7.explodeany.compat.common;

import io.github.guillex7.explodeany.compat.common.api.IExplosionUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;

public interface ICompatibilityApi {
    public Version getMinimumSupportedBukkitVersion();

    public void load();

    public IParticleUtils getParticleUtils();

    public IPlayerInventoryUtils getPlayerInventoryUtils();

    public IPlayerInteractionEventUtils getPlayerInteractionEventUtils();

    public IExplosionUtils getExplosionUtils();

    public IPersistentStorageUtils getPersistentStorageUtils();
}
