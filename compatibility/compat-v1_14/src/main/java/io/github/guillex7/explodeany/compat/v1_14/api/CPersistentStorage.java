package io.github.guillex7.explodeany.compat.v1_14.api;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.api.IPersistentStorage;
import io.github.guillex7.explodeany.compat.common.data.EanyMetaPersistentDataType;

public class CPersistentStorage implements IPersistentStorage {
    protected PersistentDataContainer persistentDataContainer;

    public CPersistentStorage(PersistentDataContainer persistentDataContainer) {
        this.persistentDataContainer = persistentDataContainer;
    }

    @Override
    public boolean isEmpty() {
        return this.persistentDataContainer.isEmpty();
    }

    @Override
    public boolean has(Plugin namespace, String key, EanyMetaPersistentDataType metaDataType) {
        return this.persistentDataContainer.has(new NamespacedKey(namespace, key),
                this.getPersistentDataTypeFromMeta(metaDataType));
    }

    public PersistentDataType<?, ?> getPersistentDataTypeFromMeta(EanyMetaPersistentDataType meta) {
        switch (meta) {
            case BYTE:
                return PersistentDataType.BYTE;
            default:
                return null;
        }
    }
}
