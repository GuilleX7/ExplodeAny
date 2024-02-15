package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.unhandled;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.compat.common.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.BaseUnhandledVanillaExplosionListener;

public abstract class BaseUnhandledKnownVanillaExplosionListener extends BaseUnhandledVanillaExplosionListener {
    @EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        Entity entity = event.getEntity();
        EntityType entityType = event.getEntityType();
        String entityTypeName = entityType.toString();

        // Special cases
        boolean isCharged = false;
        if ((entityType.equals(EntityType.CREEPER) && ((Creeper) entity).isPowered()) ||
                (entityType.equals(EntityType.WITHER_SKULL) && ((WitherSkull) entity).isCharged())) {
            entityTypeName = "CHARGED_".concat(entityTypeName);
            isCharged = true;
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(entityTypeName);
        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(entityTypeName);
        double explosionRadius = CompatibilityManager.getInstance().getApi().getExplosionUtils()
                .getExplosionRadiusAndPower(entityType, isCharged);

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
    public void unload() {
        EntityExplodeEvent.getHandlerList().unregister(this);
    }

    @Override
    protected boolean isEventHandled(EntityExplodeEvent event) {
        return super.isEventHandled(event)
                && ExplodingVanillaEntity.isEntityNameValid(event.getEntityType().toString());
    }
}
