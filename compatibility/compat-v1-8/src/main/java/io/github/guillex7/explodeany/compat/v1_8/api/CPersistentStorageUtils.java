package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.entity.Entity;

import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.data.IPersistentStorage;
import io.github.guillex7.explodeany.compat.v1_8.data.CPersistentStorage;

public class CPersistentStorageUtils implements IPersistentStorageUtils {
    private final CPersistentStorage fakePersistentStorage = new CPersistentStorage();

    @Override
    public IPersistentStorage getForEntity(final Entity entity) {
        return this.fakePersistentStorage;
    }
}
