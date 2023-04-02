package io.github.guillex7.explodeany.listener.loadable.explosion;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataType;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;

public class MagicExplosionListener extends VanillaBaseExplosionListener {
    private static final NamespacedKey MAGIC_SPAWNED_NAMESPACED_KEY = new NamespacedKey(
            Bukkit.getPluginManager().getPlugin("Magic"), "magicspawned");

    public static boolean isEntitySpawnedByMagic(Entity entity) {
        return entity.getPersistentDataContainer().has(MAGIC_SPAWNED_NAMESPACED_KEY, PersistentDataType.BYTE);
    }

    @Override
    public String getName() {
        return "Magic explosions";
    }

    @Override
    public boolean isAdvisable() {
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
