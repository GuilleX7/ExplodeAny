package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public abstract class KnownVanillaEntityExplosionListener extends BaseVanillaEntityExplosionListener {
    @EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        Entity entity = event.getEntity();

        ExplodingVanillaEntity explodingEntity = ExplodingVanillaEntity.fromEntity(entity);
        String explodingEntityName = explodingEntity.getName();
        double explosionRadius = explodingEntity.getExplosionRadius();

        if (DebugManager.getInstance().isDebugEnabled()) {
            this.logDebugMessage(explodingEntityName);
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(explodingEntityName);
        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(explodingEntityName);

        if (materialConfigurations == null || entityConfiguration == null || explosionRadius == 0d) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getLocation(), explosionRadius)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.blockList());
        }
    }

    @Override
    protected boolean isEventHandled(EntityExplodeEvent event) {
        return super.isEventHandled(event)
                && ExplodingVanillaEntity.isEntityNameValid(event.getEntityType().name());
    }

    protected abstract void logDebugMessage(String entityTypeName);
}
