package io.github.guillex7.explodeany.compat.v1_14;

import io.github.guillex7.explodeany.compat.common.ACompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.v1_14.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_14.api.CSoundUtils;

public class CompatibilityApi extends ACompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 14);

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
