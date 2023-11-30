package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.api.IPersistentStorage;
import io.github.guillex7.explodeany.compat.common.data.MetaPersistentDataType;

public class CPersistentStorage implements IPersistentStorage {
    @Override
    public boolean isEmpty() {
        // Not supported
        return true;
    }

    @Override
    public boolean has(Plugin namespace, String key, MetaPersistentDataType dataType) {
        // Not supported
        return false;
    }
}
