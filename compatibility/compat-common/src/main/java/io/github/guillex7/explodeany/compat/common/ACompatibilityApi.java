package io.github.guillex7.explodeany.compat.common;

import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.common.api.ISoundUtils;

public abstract class ACompatibilityApi {
    protected IBlockDataUtils blockDataUtils;
    protected IParticleUtils particleUtils;
    protected IPersistentStorageUtils persistentStorageUtils;
    protected IPlayerInteractionEventUtils playerInteractionEventUtils;
    protected IPlayerInventoryUtils playerInventoryUtils;
    protected IBukkitListenerUtils bukkitListenerUtils;
    protected IBukkitUtils bukkitUtils;
    protected ISoundUtils soundUtils;

    public boolean isEnvironmentSuitable() {
        return true;
    }

    public String getName() {
        return "Bukkit";
    }

    public String getFullName() {
        return String.format("%s %s+", this.getName(), this.getMinimumSupportedBukkitVersion().toString());
    }

    public IBlockDataUtils getBlockDataUtils() {
        return this.blockDataUtils;
    }

    public IParticleUtils getParticleUtils() {
        return this.particleUtils;
    }

    public IPersistentStorageUtils getPersistentStorageUtils() {
        return this.persistentStorageUtils;
    }

    public IPlayerInteractionEventUtils getPlayerInteractionEventUtils() {
        return this.playerInteractionEventUtils;
    }

    public IPlayerInventoryUtils getPlayerInventoryUtils() {
        return this.playerInventoryUtils;
    }

    public IBukkitListenerUtils getBukkitListenerUtils() {
        return this.bukkitListenerUtils;
    }

    public IBukkitUtils getBukkitUtils() {
        return this.bukkitUtils;
    }

    public ISoundUtils getSoundUtils() {
        return this.soundUtils;
    }

    public abstract Version getMinimumSupportedBukkitVersion();

    public abstract void load();

}
