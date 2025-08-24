package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.entity.Entity;

import io.github.guillex7.explodeany.compat.common.data.IPersistentStorage;

public interface IPersistentStorageUtils {
    IPersistentStorage getForEntity(Entity entity);
}
