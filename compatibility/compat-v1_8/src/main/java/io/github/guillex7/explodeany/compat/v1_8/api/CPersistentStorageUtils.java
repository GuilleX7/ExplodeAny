package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.entity.Entity;

import io.github.guillex7.explodeany.compat.common.api.IPersistentStorage;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;

public class CPersistentStorageUtils implements IPersistentStorageUtils {
    private final CPersistentStorage fakePersistentStorage = new CPersistentStorage();

    @Override
    public IPersistentStorage getForEntity(Entity entity) {
        return this.fakePersistentStorage;
    }
}
