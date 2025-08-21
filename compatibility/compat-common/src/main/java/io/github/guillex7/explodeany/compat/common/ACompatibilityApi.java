package io.github.guillex7.explodeany.compat.common;

import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.common.api.IPlayerInventoryUtils;

public abstract class ACompatibilityApi {
    public boolean isEnvironmentSuitable() {
        return true;
    }

    public String getName() {
        return "Bukkit";
    }

    public String getFullName() {
        return String.format("%s %s+", this.getName(), this.getMinimumSupportedBukkitVersion().toString());
    }

    public abstract Version getMinimumSupportedBukkitVersion();

    public abstract void load();

    public abstract IBlockDataUtils getBlockDataUtils();

    public abstract IParticleUtils getParticleUtils();

    public abstract IPersistentStorageUtils getPersistentStorageUtils();

    public abstract IPlayerInteractionEventUtils getPlayerInteractionEventUtils();

    public abstract IPlayerInventoryUtils getPlayerInventoryUtils();

    public abstract IBukkitListenerUtils getBukkitListenerUtils();

    public abstract IBukkitUtils getBukkitUtils();
}
