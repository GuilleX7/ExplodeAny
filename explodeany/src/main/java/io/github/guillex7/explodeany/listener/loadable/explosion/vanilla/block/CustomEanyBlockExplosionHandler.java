package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.bukkit.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.CustomVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionContext;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class CustomEanyBlockExplosionHandler implements EanyBlockExplosionHandler {
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
    public boolean isEventHandled(final EanyBlockExplodeEvent event) {
        return !ExplodingVanillaEntity.isEntityNameValid(event.getBlockMaterial());
    }

    @Override
    public void onBlockExplode(final EanyBlockExplodeEvent event) {
        final String entityBlockName = event.getBlockMaterial();

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

        final ExplosionContext explosionContext = ExplosionContext.of(materialConfigurations, entityConfiguration,
                event.getBlockLocation(), explosionRadius);
        explosionContext.setCoreProtectEntityIdentifier(String.format("#%s", entityBlockName.toLowerCase()));

        if (ExplosionManager.getInstance().manageExplosion(explosionContext)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.getBlockList(),
                    event.getBlockLocation());
        }
    }

    @Override
    public void unload() {
        // Do nothing
    }
}
