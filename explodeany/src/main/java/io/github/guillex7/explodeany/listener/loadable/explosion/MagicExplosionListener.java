package io.github.guillex7.explodeany.listener.loadable.explosion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.api.IPersistentStorage.MetaPersistentDataType;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;

public class MagicExplosionListener extends VanillaBaseExplosionListener {
    private static final Plugin MAGIC_SPAWNED_NAMESPACE = Bukkit.getPluginManager().getPlugin("Magic");
    private static final String MAGIC_SPAWNED_KEY = "magicspawned";

    public static boolean isEntitySpawnedByMagic(Entity entity) {
        return CompatibilityManager.getInstance().getApi().getPersistentStorageUtils().getForEntity(entity)
                .has(MAGIC_SPAWNED_NAMESPACE, MAGIC_SPAWNED_KEY, MetaPersistentDataType.BYTE);
    }

    @Override
    public String getName() {
        return "Magic explosions";
    }

    @Override
    public boolean isAnnounceable() {
        return true;
    }

    @Override
    protected boolean isEventHandled(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        return entity != null && isEntitySpawnedByMagic(entity);
    }

    @Override
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance().getRegisteredEntityConfiguration("MagicEntity");
    }
}
