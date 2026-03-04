package io.github.guillex7.explodeany.compat.v1_14.api;

import org.bukkit.entity.Entity;

import io.github.guillex7.explodeany.compat.common.data.IPersistentStorage;
import io.github.guillex7.explodeany.compat.v1_14.data.CPersistentStorage;

public class CPersistentStorageUtils extends io.github.guillex7.explodeany.compat.v1_13.api.CPersistentStorageUtils {
    @Override
    public IPersistentStorage getForEntity(final Entity entity) {
        return new CPersistentStorage(entity.getPersistentDataContainer());
    }
}
