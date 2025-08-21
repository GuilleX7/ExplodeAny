package io.github.guillex7.explodeany.compat.v1_20;

import io.github.guillex7.explodeany.compat.common.ACompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_13.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_20.api.CBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInventoryUtils;

public class CompatibilityApi extends ACompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 20);

    private IBlockDataUtils blockDataUtils;
    private IParticleUtils particleUtils;
    private IPersistentStorageUtils persistentStorageUtils;
    private IPlayerInteractionEventUtils playerInteractionEventUtils;
    private IPlayerInventoryUtils playerInventoryUtils;
    private IBukkitListenerUtils bukkitListenerUtils;
    private IBukkitUtils bukkitUtils;

    @Override
    public void load() {
        this.blockDataUtils = new CBlockDataUtils();
        this.particleUtils = new CParticleUtils();
        this.persistentStorageUtils = new CPersistentStorageUtils();
        this.playerInteractionEventUtils = new CPlayerInteractionEventUtils();
        this.playerInventoryUtils = new CPlayerInventoryUtils();
        this.bukkitListenerUtils = new CBukkitListenerUtils();
        this.bukkitUtils = new CBukkitUtils();
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

    @Override
    public IBukkitUtils getBukkitUtils() {
        return bukkitUtils;
    }
}
