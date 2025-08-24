package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.EnumSet;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.configuration.section.specific.TNTSpecificEntityConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionFlag;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.listener.loadable.EntitySpawnListener;
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
    public boolean isEventHandled(final EntityExplodeEvent event) {
        return ExplodingVanillaEntity.isEntityNameValid(event.getEntityType().name());
    }

    @Override
    public void onEntityExplode(final EntityExplodeEvent event) {
        final Entity entity = event.getEntity();

        final ExplodingVanillaEntity explodingEntity = ExplodingVanillaEntity.fromEntity(entity);
        final double explosionRadius = explodingEntity.getExplosionRadius();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected vanilla entity explosion. Entity type: {0}",
                    explodingEntity.getName());
        }

        final Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(explodingEntity);
        final EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(explodingEntity);

        if (materialConfigurations == null || entityConfiguration == null || explosionRadius == 0d) {
            return;
        }

        final EnumSet<ExplosionFlag> explosionFlags = EnumSet.noneOf(ExplosionFlag.class);
        if (explodingEntity == ExplodingVanillaEntity.PRIMED_TNT
                && entityConfiguration.getSpecificConfiguration() instanceof TNTSpecificEntityConfiguration
                && entity.hasMetadata(EntitySpawnListener.TNT_PRIMED_LOCATION_TAG)) {
            final TNTSpecificEntityConfiguration tntSpecificConfiguration = (TNTSpecificEntityConfiguration) entityConfiguration
                    .getSpecificConfiguration();
            final Location primeLocation = (Location) entity.getMetadata(EntitySpawnListener.TNT_PRIMED_LOCATION_TAG)
                    .get(0).value();
            final double squaredDistanceToPrimeLocation = primeLocation.distanceSquared(event.getLocation());

            if (tntSpecificConfiguration
                    .getMinimumSquaredDistanceToDamageBlocksUnderwater() > squaredDistanceToPrimeLocation) {
                explosionFlags.add(ExplosionFlag.FORCE_DISABLE_VANILLA_UNDERWATER_DAMAGE);
            }
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getLocation(), explosionRadius, explosionFlags)) {
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
