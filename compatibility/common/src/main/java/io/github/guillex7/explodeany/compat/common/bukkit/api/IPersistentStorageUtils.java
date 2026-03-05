package io.github.guillex7.explodeany.compat.common.bukkit.api;

import org.bukkit.entity.Entity;

import io.github.guillex7.explodeany.compat.common.bukkit.data.IPersistentStorage;

public interface IPersistentStorageUtils {
    IPersistentStorage getForEntity(Entity entity);
}
