package io.github.guillex7.explodeany.compat.v1_13;

import io.github.guillex7.explodeany.compat.common.ICompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.api.IExplosionUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_13.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_13.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CExplosionUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInventoryUtils;

public class CompatibilityApi implements ICompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 13);

    private IBlockDataUtils blockDataUtils;
    private IExplosionUtils explosionUtils;
    private IParticleUtils particleUtils;
    private IPersistentStorageUtils persistentStorageUtils;
    private IPlayerInteractionEventUtils playerInteractionEventUtils;
    private IPlayerInventoryUtils playerInventoryUtils;

    public void load() {
        this.blockDataUtils = new CBlockDataUtils();
        this.explosionUtils = new CExplosionUtils();
        this.particleUtils = new CParticleUtils();
        this.persistentStorageUtils = new CPersistentStorageUtils();
        this.playerInteractionEventUtils = new CPlayerInteractionEventUtils();
        this.playerInventoryUtils = new CPlayerInventoryUtils();
    }

    @Override
    public Version getMinimumSupportedBukkitVersion() {
        return minimumSupportedBukkitVersion;
    }

    @Override
    public IParticleUtils getParticleUtils() {
        return particleUtils;
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

    @Override
    public IBlockDataUtils getBlockDataUtils() {
        return blockDataUtils;
    }
}
