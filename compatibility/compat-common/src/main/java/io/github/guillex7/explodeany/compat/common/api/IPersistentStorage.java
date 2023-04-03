package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.plugin.Plugin;

public interface IPersistentStorage {
    boolean isEmpty();

    boolean has(Plugin namespace, String key, MetaPersistentDataType dataType);

    public enum MetaPersistentDataType {
        BYTE
    }
}
