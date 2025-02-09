package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class RegularVanillaEntityExplosionHandler implements VanillaEntityExplosionHandler {
    private RegularVanillaEntityConfiguration configuration;

    @Override
    public boolean shouldBeLoaded() {
        return ConfigurationManager.getInstance()
                .isConfigurationSectionLoaded(RegularVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public void load() {
        this.configuration = (RegularVanillaEntityConfiguration) ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(RegularVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public boolean isEventHandled(EntityExplodeEvent event) {
        return ExplodingVanillaEntity.isEntityNameValid(event.getEntityType().name());
    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        ExplodingVanillaEntity explodingEntity = ExplodingVanillaEntity.fromEntity(entity);
        String explodingEntityName = explodingEntity.getName();
        double explosionRadius = explodingEntity.getExplosionRadius();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected vanilla entity explosion. Entity type: {0}",
                    explodingEntityName);
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(explodingEntityName);
        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(explodingEntityName);

        if (materialConfigurations == null || entityConfiguration == null || explosionRadius == 0d) {
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
