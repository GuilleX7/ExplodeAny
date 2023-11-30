package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.data.MetaPersistentDataType;

public interface IPersistentStorage {
    boolean isEmpty();

    boolean has(Plugin namespace, String key, MetaPersistentDataType dataType);
}
