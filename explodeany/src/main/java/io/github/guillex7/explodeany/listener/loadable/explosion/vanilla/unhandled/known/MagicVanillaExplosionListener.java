package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.unhandled.known;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.compat.common.data.MetaPersistentDataType;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.MagicVanillaEntityConfiguration;
import io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.unhandled.BaseUnhandledKnownVanillaExplosionListener;

public class MagicVanillaExplosionListener extends BaseUnhandledKnownVanillaExplosionListener {
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
        return super.isEventHandled(event) && isEntitySpawnedByMagic(event.getEntity());
    }

    @Override
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance()
                .getRegisteredLoadableConfigurationSection(MagicVanillaEntityConfiguration.getConfigurationId());
    }
}
