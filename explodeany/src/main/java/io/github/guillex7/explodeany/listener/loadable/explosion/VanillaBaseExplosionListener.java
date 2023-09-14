package io.github.guillex7.explodeany.listener.loadable.explosion;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.compat.common.api.IExplosionUtils.ExplosionParameters;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;

public abstract class VanillaBaseExplosionListener extends BaseExplosionListener {
    @EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        EntityType entityType = event.getEntityType();
        String entityTypeName = entityType.toString();
        boolean isCharged = false;
        Map<Material, EntityMaterialConfiguration> materialConfigurations = null;

        // Special cases
        if (event.getEntityType().equals(EntityType.CREEPER)) {
            Creeper creeper = (Creeper) event.getEntity();
            if (creeper.isPowered()) {
                entityTypeName = "CHARGED_".concat(entityTypeName);
                isCharged = true;
            }
        } else if (event.getEntityType().equals(EntityType.WITHER_SKULL)) {
            WitherSkull witherSkull = (WitherSkull) event.getEntity();
            if (witherSkull.isCharged()) {
                entityTypeName = "CHARGED_".concat(entityTypeName);
                isCharged = true;
            }
        }

        materialConfigurations = this.configuration.getEntityMaterialConfigurations().get(entityTypeName);
        if (materialConfigurations == null) {
            return;
        }

        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(entityTypeName);

        ExplosionParameters explosionParameters = CompatibilityManager.getInstance().getApi().getExplosionUtils()
                .getExplosionRadiusAndPower(entityType, isCharged);

        if (!explosionParameters.areValid()) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getLocation(), explosionParameters.getRadius(), explosionParameters.getPower())) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.blockList());
        }
    }

    @Override
    public void unload() {
        EntityExplodeEvent.getHandlerList().unregister(this);
    }

    protected boolean isEventHandled(EntityExplodeEvent event) {
        return event.getEntity() != null
                && !ExplosionManager.getInstance().isEntitySpawnedByExplosionManager(event.getEntity())
                && !ConfigurationManager.getInstance().getDisabledWorlds()
                        .contains(event.getLocation().getWorld().getName());
    }
}