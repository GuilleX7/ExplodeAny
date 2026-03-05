package io.github.guillex7.explodeany.compat.common.bukkit;

import java.util.logging.Logger;

import io.github.guillex7.explodeany.compat.common.LoadableApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.api.ISoundUtils;

public abstract class ABukkitApi implements LoadableApi {
    protected IBlockDataUtils blockDataUtils;
    protected IParticleUtils particleUtils;
    protected IPersistentStorageUtils persistentStorageUtils;
    protected IPlayerInteractionEventUtils playerInteractionEventUtils;
    protected IPlayerInventoryUtils playerInventoryUtils;
    protected IBukkitListenerUtils bukkitListenerUtils;
    protected IBukkitUtils bukkitUtils;
    protected ISoundUtils soundUtils;

    @Override
    public boolean isEnvironmentSuitable(final Version bukkitVersion) {
        return bukkitVersion.isEqualOrAfter(this.getMinimumSupportedBukkitVersion());
    }

    @Override
    public String getName() {
        return String.format("%s %s+", this.getPlatform(), this.getMinimumSupportedBukkitVersion());
    }

    protected String getPlatform() {
        return "Bukkit";
    }

    @Override
    public void load(final Version bukkitVersion, final Logger logger) {
        this.instantiate();

        logger.info(String.format("Loaded compatibility for %s %s+ (server: %s)", this.getPlatform(),
                this.getMinimumSupportedBukkitVersion(), bukkitVersion));
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

    @Override
    public void unload() {
        /* Do nothing */
    }

    protected abstract void instantiate();

    protected abstract Version getMinimumSupportedBukkitVersion();
}
