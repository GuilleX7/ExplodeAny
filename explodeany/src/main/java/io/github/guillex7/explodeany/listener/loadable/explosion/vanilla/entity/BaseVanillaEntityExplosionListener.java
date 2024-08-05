package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.listener.loadable.explosion.BaseConfigurableExplosionListener;

public abstract class BaseVanillaEntityExplosionListener extends BaseConfigurableExplosionListener {
    protected boolean isEventHandled(EntityExplodeEvent event) {
        return !event.isCancelled() && event.getEntity() != null
                && !ExplosionManager.getInstance().isEntitySpawnedByExplosionManager(event.getEntity())
                && !ConfigurationManager.getInstance().getDisabledWorlds()
                        .contains(event.getLocation().getWorld().getName());
    }

    @Override
    public void unload() {
        EntityExplodeEvent.getHandlerList().unregister(this);
    }
}
