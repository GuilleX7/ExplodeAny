package io.github.guillex7.explodeany.listener.loadable.explosion;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.explosion.metadata.ExplosionMetadata;

public class EanyTaggedExplosionListener implements LoadableListener {
    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        /* Nothing to do */
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
        return !event.isCancelled() && event.getEntity() != null
                && ExplosionManager.getInstance().isEntitySpawnedByExplosionManager(event.getEntity());
    }

    @Override
    public void unload() {
        EntityExplodeEvent.getHandlerList().unregister(this);
    }
}
