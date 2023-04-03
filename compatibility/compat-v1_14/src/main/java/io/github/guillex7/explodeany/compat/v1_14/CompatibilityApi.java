package io.github.guillex7.explodeany.compat.v1_14;

import io.github.guillex7.explodeany.compat.common.ICompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.api.IExplosionUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_13.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CExplosionUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInventoryUtils;

public class CompatibilityApi implements ICompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 14);

    private CParticleUtils effectUtils;
    private CPlayerInventoryUtils playerInventoryUtils;
    private CPlayerInteractionEventUtils playerInteractionEventUtils;
    private CExplosionUtils explosionUtils;
    private CPersistentStorageUtils persistentStorageUtils;

    public void load() {
        this.effectUtils = new CParticleUtils();
        this.playerInventoryUtils = new CPlayerInventoryUtils();
        this.playerInteractionEventUtils = new CPlayerInteractionEventUtils();
        this.explosionUtils = new CExplosionUtils();
        this.persistentStorageUtils = new CPersistentStorageUtils();
    }

    @Override
    public Version getMinimumSupportedBukkitVersion() {
        return minimumSupportedBukkitVersion;
    }

    @Override
    public IParticleUtils getParticleUtils() {
        return effectUtils;
    }

    @Override
    public IPlayerInventoryUtils getPlayerInventoryUtils() {
        return playerInventoryUtils;
    }

    @Override
    public IPlayerInteractionEventUtils getPlayerInteractionEventUtils() {
        return playerInteractionEventUtils;
    }

    @Override
    public IExplosionUtils getExplosionUtils() {
        return explosionUtils;
    }

    @Override
    public IPersistentStorageUtils getPersistentStorageUtils() {
        return persistentStorageUtils;
    }
}
