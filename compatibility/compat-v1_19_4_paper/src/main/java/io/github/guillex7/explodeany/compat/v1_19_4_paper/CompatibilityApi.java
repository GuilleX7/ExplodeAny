package io.github.guillex7.explodeany.compat.v1_19_4_paper;

import io.github.guillex7.explodeany.compat.common.ACompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.environment.Environment;
import io.github.guillex7.explodeany.compat.v1_16_1.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_16_1.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.v1_16_1.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_16_1.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_16_1.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_16_1.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_16_1.api.CSoundUtils;
import io.github.guillex7.explodeany.compat.v1_19_4_paper.api.CBukkitListenerUtils;

public class CompatibilityApi extends ACompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 19, 4);

    @Override
    public void load() {
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
    public String getName() {
        return "Paper";
    }

    @Override
    public boolean isEnvironmentSuitable() {
        return Environment.isPaperBased();
    }
}
