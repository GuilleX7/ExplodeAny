package io.github.guillex7.explodeany.compat.v1_8;

import io.github.guillex7.explodeany.compat.common.ICompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.api.IExplosionUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CExplosionUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPlayerInventoryUtils;

public class CompatibilityApi implements ICompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 8);

    private IBlockDataUtils blockDataUtils;
    private IExplosionUtils explosionUtils;
    private IParticleUtils particleUtils;
    private IPersistentStorageUtils persistentStorageUtils;
    private IPlayerInteractionEventUtils playerInteractionEventUtils;
    private IPlayerInventoryUtils playerInventoryUtils;

    @Override
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
    public IBlockDataUtils getBlockDataUtils() {
        return blockDataUtils;
    }

    @Override
    public IExplosionUtils getExplosionUtils() {
        return explosionUtils;
    }

    @Override
    public IParticleUtils getParticleUtils() {
        return particleUtils;
    }

    @Override
    public IPersistentStorageUtils getPersistentStorageUtils() {
        return persistentStorageUtils;
    }

    @Override
    public IPlayerInteractionEventUtils getPlayerInteractionEventUtils() {
        return playerInteractionEventUtils;
    }

    @Override
    public IPlayerInventoryUtils getPlayerInventoryUtils() {
        return playerInventoryUtils;
    }
}
