package io.github.guillex7.explodeany.compat.v1_8_3;

import io.github.guillex7.explodeany.compat.common.ICompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_8.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CBukkitListenerUtils;

public class CompatibilityApi implements ICompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 8, 3);

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
    public IBlockDataUtils getBlockDataUtils() {
        return blockDataUtils;
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

    @Override
    public IBukkitListenerUtils getBukkitListenerUtils() {
        return bukkitListenerUtils;
    }

    @Override
    public IBukkitUtils getBukkitUtils() {
        return bukkitUtils;
    }
}
