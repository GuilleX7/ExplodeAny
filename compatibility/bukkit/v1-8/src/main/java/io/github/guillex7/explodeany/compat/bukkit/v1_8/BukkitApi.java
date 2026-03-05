package io.github.guillex7.explodeany.compat.bukkit.v1_8;

import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_8.api.CSoundUtils;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.bukkit.ABukkitApi;

public class BukkitApi extends ABukkitApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 8);

    @Override
    public void instantiate() {
        this.blockDataUtils = new CBlockDataUtils();
        this.particleUtils = new CParticleUtils();
        this.persistentStorageUtils = new CPersistentStorageUtils();
        this.playerInteractionEventUtils = new CPlayerInteractionEventUtils();
        this.playerInventoryUtils = new CPlayerInventoryUtils();
        this.bukkitListenerUtils = new CBukkitListenerUtils();
        this.bukkitUtils = new CBukkitUtils();
        this.soundUtils = new CSoundUtils();
    }

    @Override
    public Version getMinimumSupportedBukkitVersion() {
        return this.minimumSupportedBukkitVersion;
    }
}
