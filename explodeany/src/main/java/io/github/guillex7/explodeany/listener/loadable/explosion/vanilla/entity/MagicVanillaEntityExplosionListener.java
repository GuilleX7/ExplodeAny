package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.data.MetaPersistentDataType;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.MagicVanillaEntityConfiguration;

public class MagicVanillaEntityExplosionListener extends KnownVanillaEntityExplosionListener {
    private static final Plugin MAGIC_PLUGIN = Bukkit.getPluginManager().getPlugin("Magic");
    private static final String MAGIC_SPAWNED_KEY = "magicspawned";

    public static boolean isEntitySpawnedByMagic(Entity entity) {
        return MAGIC_PLUGIN != null
                && CompatibilityManager.getInstance().getApi().getPersistentStorageUtils().getForEntity(entity)
                        .has(MAGIC_PLUGIN, MAGIC_SPAWNED_KEY, MetaPersistentDataType.BYTE);
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
    protected void logDebugMessage(String entityTypeName) {
        ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected Magic entity explosion. Entity type: {0}",
                entityTypeName);
    }

    @Override
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance()
                .getRegisteredLoadableConfigurationSection(MagicVanillaEntityConfiguration.getConfigurationId());
    }
}
