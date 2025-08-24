package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.CustomVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class CustomEanyBlockExplosionListener implements EanyBlockExplosionHandler {
    private static final String UNKNOWN_BLOCK_NAME = "UNKNOWN";

    private CustomVanillaEntityConfiguration configuration;

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        this.configuration = (CustomVanillaEntityConfiguration) ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(CustomVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public void onBlockExplode(final EanyBlockExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        final String entityBlockName = event.getBlockMaterial() == null
                ? CustomEanyBlockExplosionListener.UNKNOWN_BLOCK_NAME
                : event.getBlockMaterial();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected custom block explosion. Block type: {0}",
                    entityBlockName);
        }

        final Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(entityBlockName);
        final EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(entityBlockName);

        if (materialConfigurations == null || entityConfiguration == null) {
            return;
        }

        final double explosionRadius = entityConfiguration.getExplosionRadius();

        if (explosionRadius == 0d) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getBlockLocation(), explosionRadius)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.getBlockList(),
                    event.getBlockLocation());
        }
    }

    @Override
    public boolean isEventHandled(final EanyBlockExplodeEvent event) {
        return true;
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
