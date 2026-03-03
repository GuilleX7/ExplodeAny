package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.explosion.metadata.ExplosionMetadata;

public class EanyVanillaEntityExplosionHandler implements VanillaEntityExplosionHandler {
    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        /* Nothing to do */
    }

    @Override
    public boolean isEventHandled(final EntityExplodeEvent event) {
        return ExplosionManager.getInstance().isEntitySpawnedByExplosionManager(event.getEntity());
    }

    @Override
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        final ExplosionMetadata explosionMetadata = ExplosionManager.getInstance()
                .getExplosionManagerMetadataFromEntity(event.getEntity());
        ExplosionManager.getInstance().removeHandledBlocksFromList(explosionMetadata.materialConfigurations,
                event.blockList(), event.getLocation());
        explosionMetadata.dropCollector.dropCollectedItems(event.getLocation());
    }

    @Override
    public void unload() {
        /* Do nothing */
    }
}
