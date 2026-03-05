package io.github.guillex7.explodeany.compat.bukkit.v1_19_4_paper;

import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api.CSoundUtils;
import io.github.guillex7.explodeany.compat.bukkit.v1_19_4_paper.api.CBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.Environment;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.bukkit.ABukkitApi;

public class BukkitApi extends ABukkitApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 19, 4);

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

    @Override
    public String getPlatform() {
        return "Paper";
    }

    @Override
    public boolean isEnvironmentSuitable(final Version bukkitVersion) {
        return super.isEnvironmentSuitable(bukkitVersion) && Environment.isPaperBased();
    }
}
