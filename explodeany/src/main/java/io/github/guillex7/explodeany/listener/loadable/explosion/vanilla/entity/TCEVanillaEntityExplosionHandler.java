package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import dev.pixelmania.throwablecreepereggs.TCEApi;
import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.TCEVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class TCEVanillaEntityExplosionHandler implements VanillaEntityExplosionHandler {
    private TCEVanillaEntityConfiguration configuration;

    @Override
    public boolean shouldBeLoaded() {
        return ConfigurationManager.getInstance()
                .isConfigurationSectionLoaded(TCEVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public void load() {
        this.configuration = (TCEVanillaEntityConfiguration) ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(TCEVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public boolean isEventHandled(EntityExplodeEvent event) {
        return ExplodingVanillaEntity.isEntityNameValid(event.getEntityType().name())
                && TCEApi.entityIsTCE(event.getEntity());
    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
        final Entity entity = event.getEntity();

        final ExplodingVanillaEntity explodingEntity = ExplodingVanillaEntity.fromEntity(entity);
        final double explosionRadius = explodingEntity.getExplosionRadius();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO,
                    "Detected ThrowableCreeperEggs explosion. Entity type: {0}",
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
                event.getLocation(), explosionRadius, false)) {
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
