package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.unhandled.known;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.ExplodingVanillaEntityConfiguration;
import io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.unhandled.BaseUnhandledKnownVanillaExplosionListener;

public final class ExplodingVanillaExplosionListener extends BaseUnhandledKnownVanillaExplosionListener {
    public static boolean isEntityVanilla(Entity entity) {
        return !MagicVanillaExplosionListener.isEntitySpawnedByMagic(entity);
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
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance()
                .getRegisteredLoadableConfigurationSection(ExplodingVanillaEntityConfiguration.getConfigurationId());
    }
}
