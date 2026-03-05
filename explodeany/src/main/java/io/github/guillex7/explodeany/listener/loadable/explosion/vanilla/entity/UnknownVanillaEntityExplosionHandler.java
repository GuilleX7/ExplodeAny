package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.CustomVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionContext;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class UnknownVanillaEntityExplosionHandler implements VanillaEntityExplosionHandler {
    private static final String UNKNOWN_ENTITY_NAME = "UNKNOWN_ENTITY";
    private static final String UNKNOWN_NAME = "UNKNOWN";

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
    public boolean isEventHandled(final EntityExplodeEvent event) {
        return event.getEntity() == null;
    }

    @Override
    public void onEntityExplode(final EntityExplodeEvent event) {
        String entityTypeName = UNKNOWN_ENTITY_NAME;

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO,
                    "Detected custom entity explosion. Entity type: {0} (also known as {1})",
                    new Object[] { entityTypeName, UNKNOWN_NAME });
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(entityTypeName);
        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(entityTypeName);

        if (materialConfigurations == null || entityConfiguration == null) {
            entityTypeName = UNKNOWN_NAME;
            materialConfigurations = this.configuration.getEntityMaterialConfigurations().get(UNKNOWN_NAME);
            entityConfiguration = this.configuration.getEntityConfigurations().get(UNKNOWN_NAME);

            if (materialConfigurations == null || entityConfiguration == null) {
                return;
            }
        }

        final double explosionRadius = entityConfiguration.getExplosionRadius();
        if (explosionRadius == 0d) {
            return;
        }

        final ExplosionContext explosionContext = ExplosionContext.of(materialConfigurations, entityConfiguration,
                event.getLocation(), explosionRadius);
        explosionContext.setCoreProtectEntityIdentifier(String.format("#%s", entityTypeName.toLowerCase()));

        if (ExplosionManager.getInstance().manageExplosion(explosionContext)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.blockList(),
                    event.getLocation());
        }
    }

    @Override
    public void unload() {
        /* Do nothing */
    }
}
