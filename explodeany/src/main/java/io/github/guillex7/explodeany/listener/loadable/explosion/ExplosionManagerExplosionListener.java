package io.github.guillex7.explodeany.listener.loadable.explosion;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.explosion.metadata.ExplosionMetadata;
import io.github.guillex7.explodeany.listener.loadable.LoadableListener;

public class ExplosionManagerExplosionListener extends LoadableListener {
    @Override
    public String getName() {
        return "ExplosionManager explosions";
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

        ExplosionMetadata explosionMetadata = ExplosionManager.getInstance()
                .getExplosionManagerMetadataFromEntity(event.getEntity());
        ExplosionManager.getInstance().removeHandledBlocksFromList(explosionMetadata.materialConfigurations,
                event.blockList());
        explosionMetadata.dropCollector.dropCollectedItems(event.getLocation());
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
