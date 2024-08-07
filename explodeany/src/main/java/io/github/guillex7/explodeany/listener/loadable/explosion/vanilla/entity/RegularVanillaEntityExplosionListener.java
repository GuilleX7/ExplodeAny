package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.logging.Level;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;

public final class RegularVanillaEntityExplosionListener extends KnownVanillaEntityExplosionListener {
    public static boolean isEntityVanilla(Entity entity) {
        return !MagicVanillaEntityExplosionListener.isEntitySpawnedByMagic(entity);
    }

    @Override
    public String getName() {
        return "Vanilla explosions";
    }

    @Override
    public boolean isAnnounceable() {
        return true;
    }

    @Override
    protected boolean isEventHandled(EntityExplodeEvent event) {
        return super.isEventHandled(event) && isEntityVanilla(event.getEntity());
    }

    @Override
    protected void logDebugMessage(String entityTypeName) {
        ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected vanilla entity explosion. Entity type: {0}",
                entityTypeName);
    }

    @Override
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(RegularVanillaEntityConfiguration.getConfigurationId());
    }
}
