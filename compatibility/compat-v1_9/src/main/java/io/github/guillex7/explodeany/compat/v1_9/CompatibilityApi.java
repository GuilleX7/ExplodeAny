package io.github.guillex7.explodeany.compat.v1_9;

import io.github.guillex7.explodeany.compat.common.ICompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CBukkitListenerUtils;

public class CompatibilityApi implements ICompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 9);

    private IBlockDataUtils blockDataUtils;
    private IParticleUtils particleUtils;
    private IPersistentStorageUtils persistentStorageUtils;
    private IPlayerInteractionEventUtils playerInteractionEventUtils;
    private IPlayerInventoryUtils playerInventoryUtils;
    private IBukkitListenerUtils bukkitListenerUtils;

    public void load() {
        this.blockDataUtils = new CBlockDataUtils();
        this.particleUtils = new CParticleUtils();
        this.persistentStorageUtils = new CPersistentStorageUtils();
        this.playerInteractionEventUtils = new CPlayerInteractionEventUtils();
        this.playerInventoryUtils = new CPlayerInventoryUtils();
        this.bukkitListenerUtils = new CBukkitListenerUtils();
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
    public IPersistentStorageUtils getPersistentStorageUtils() {
        return persistentStorageUtils;
    }

    @Override
    public IBlockDataUtils getBlockDataUtils() {
        return blockDataUtils;
    }

    @Override
    public IBukkitListenerUtils getBukkitListenerUtils() {
        return bukkitListenerUtils;
    }
}
