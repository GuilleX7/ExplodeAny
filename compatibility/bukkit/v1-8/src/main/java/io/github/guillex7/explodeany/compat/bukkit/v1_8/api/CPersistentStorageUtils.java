package io.github.guillex7.explodeany.compat.bukkit.v1_8.api;

import org.bukkit.entity.Entity;

import io.github.guillex7.explodeany.compat.bukkit.v1_8.data.CPersistentStorage;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IPersistentStorageUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.data.IPersistentStorage;

public class CPersistentStorageUtils implements IPersistentStorageUtils {
    private final CPersistentStorage fakePersistentStorage;

    public CPersistentStorageUtils() {
        this.fakePersistentStorage = new CPersistentStorage();
    }

    @Override
    public IPersistentStorage getForEntity(final Entity entity) {
        return this.fakePersistentStorage;
    }
}
