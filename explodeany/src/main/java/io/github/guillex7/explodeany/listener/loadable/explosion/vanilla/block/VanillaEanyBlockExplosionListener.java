package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class VanillaEanyBlockExplosionListener implements EanyBlockExplosionHandler {
    private RegularVanillaEntityConfiguration configuration;

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        this.configuration = (RegularVanillaEntityConfiguration) ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(RegularVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public void onBlockExplode(final EanyBlockExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        final ExplodingVanillaEntity explodingEntity = ExplodingVanillaEntity
                .fromEntityTypeName(event.getBlockMaterial());
        final double explosionRadius = explodingEntity.getExplosionRadius();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected vanilla block explosion. Block ID: {0}",
                    explodingEntity.getName());
        }

        final Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(explodingEntity);
        final EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(explodingEntity);

        if (materialConfigurations == null || entityConfiguration == null || explosionRadius == 0d) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getBlockLocation(), explosionRadius)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations,
                    event.getBlockList(), event.getBlockLocation());
        }
    }

    @Override
    public boolean isEventHandled(final EanyBlockExplodeEvent event) {
        return event.getBlockMaterial() != null
                && ExplodingVanillaEntity.isEntityNameValid(event.getBlockMaterial());
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
