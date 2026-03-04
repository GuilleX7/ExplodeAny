package io.github.guillex7.explodeany.compat.v1_14.data;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.data.EanyMetaPersistentDataType;
import io.github.guillex7.explodeany.compat.common.data.IPersistentStorage;

public class CPersistentStorage implements IPersistentStorage {
    protected PersistentDataContainer persistentDataContainer;

    public CPersistentStorage(final PersistentDataContainer persistentDataContainer) {
        this.persistentDataContainer = persistentDataContainer;
    }

    @Override
    public boolean isEmpty() {
        return this.persistentDataContainer.isEmpty();
    }

    @Override
    public boolean has(final Plugin namespace, final String key, final EanyMetaPersistentDataType metaDataType) {
        return this.persistentDataContainer.has(new NamespacedKey(namespace, key),
                this.getPersistentDataTypeFromMeta(metaDataType));
    }

    public PersistentDataType<?, ?> getPersistentDataTypeFromMeta(final EanyMetaPersistentDataType meta) {
        switch (meta) {
            case BYTE:
                return PersistentDataType.BYTE;
            default:
                return null;
        }
    }
}
