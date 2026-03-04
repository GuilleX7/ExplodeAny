package io.github.guillex7.explodeany.compat.v1_9;

import io.github.guillex7.explodeany.compat.common.ACompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.v1_9.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_9.api.CSoundUtils;

public class CompatibilityApi extends ACompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 9);

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
}
