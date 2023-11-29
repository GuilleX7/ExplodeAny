package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.listener.loadable.LoadableListener;

public class HandledVanillaExplosionListener implements LoadableListener {
    @Override
    public String getName() {
        return "Overridden explosions manager";
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public boolean isAnnounceable() {
        return false;
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = ExplosionManager.getInstance()
                .getMaterialConfigurationsFromEntity(event.getEntity());
        ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.blockList());
    }

    private boolean isEventHandled(EntityExplodeEvent event) {
        return event.getEntity() != null
                && ExplosionManager.getInstance().isEntitySpawnedByExplosionManager(event.getEntity());
    }

    @Override
    public void unload() {
        EntityExplodeEvent.getHandlerList().unregister(this);
    }
}
