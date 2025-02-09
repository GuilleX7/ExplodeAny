package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.CustomVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class CustomVanillaEntityExplosionHandler implements VanillaEntityExplosionHandler {
    private CustomVanillaEntityConfiguration configuration;

    @Override
    public boolean shouldBeLoaded() {
        return ConfigurationManager.getInstance()
                .isConfigurationSectionLoaded(CustomVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public void load() {
        this.configuration = (CustomVanillaEntityConfiguration) ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(CustomVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public boolean isEventHandled(EntityExplodeEvent event) {
        return !ExplodingVanillaEntity.isEntityNameValid(event.getEntityType().toString());
    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        EntityType entityType = event.getEntityType();
        String entityTypeName = entityType.toString();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected custom entity explosion. Entity type: {0}",
                    entityTypeName);
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(entityTypeName);
        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(entityTypeName);

        if (materialConfigurations == null || entityConfiguration == null) {
            return;
        }

        double explosionRadius = entityConfiguration.getExplosionRadius();

        if (explosionRadius == 0d) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getLocation(), explosionRadius, false)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.blockList());
        }
    }

    @Override
    public void unload() {
        /* Do nothing */
    }
}
