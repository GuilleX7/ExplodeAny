package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.api.IPersistentStorage;

public class CPersistentStorage implements IPersistentStorage {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean has(Plugin namespace, String key, MetaPersistentDataType dataType) {
        return false;
    }
}
