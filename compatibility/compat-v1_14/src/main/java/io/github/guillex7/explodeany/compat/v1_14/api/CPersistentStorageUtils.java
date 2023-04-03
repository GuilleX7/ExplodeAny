package io.github.guillex7.explodeany.compat.v1_14.api;

import org.bukkit.entity.Entity;

import io.github.guillex7.explodeany.compat.common.api.IPersistentStorage;
import io.github.guillex7.explodeany.compat.common.api.IPersistentStorageUtils;

public class CPersistentStorageUtils implements IPersistentStorageUtils {
    @Override
    public IPersistentStorage getForEntity(Entity entity) {
        return new CPersistentStorage(entity.getPersistentDataContainer());
    }
}
