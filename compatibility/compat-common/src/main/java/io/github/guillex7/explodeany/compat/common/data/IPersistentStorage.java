package io.github.guillex7.explodeany.compat.common.data;

import org.bukkit.plugin.Plugin;

public interface IPersistentStorage {
    boolean isEmpty();

    boolean has(Plugin namespace, String key, EanyMetaPersistentDataType dataType);
}
