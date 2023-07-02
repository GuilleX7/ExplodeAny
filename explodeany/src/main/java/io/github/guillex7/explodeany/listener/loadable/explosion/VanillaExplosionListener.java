package io.github.guillex7.explodeany.listener.loadable.explosion;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;

public final class VanillaExplosionListener extends VanillaBaseExplosionListener {
    public static boolean isEntityVanilla(Entity entity) {
        return CompatibilityManager.getInstance().getApi().getPersistentStorageUtils().getForEntity(entity).isEmpty();
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
                .getRegisteredEntityConfiguration("VanillaEntity");
    }
}
