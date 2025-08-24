package io.github.guillex7.explodeany.compat.v1_8_3;

import io.github.guillex7.explodeany.compat.common.ACompatibilityApi;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CBlockDataUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CBukkitUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CParticleUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CPlayerInteractionEventUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CPlayerInventoryUtils;
import io.github.guillex7.explodeany.compat.v1_8_3.api.CSoundUtils;

public class CompatibilityApi extends ACompatibilityApi {
    private final Version minimumSupportedBukkitVersion = new Version(1, 8, 3);

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
