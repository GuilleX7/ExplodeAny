package io.github.guillex7.explodeany.compat.bukkit.v1_8.data;

import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyMetaPersistentDataType;
import io.github.guillex7.explodeany.compat.common.bukkit.data.IPersistentStorage;

public class CPersistentStorage implements IPersistentStorage {
    @Override
    public boolean isEmpty() {
        // Not supported
        return true;
    }

    @Override
    public boolean has(final Plugin namespace, final String key, final EanyMetaPersistentDataType dataType) {
        // Not supported
        return false;
    }
}
