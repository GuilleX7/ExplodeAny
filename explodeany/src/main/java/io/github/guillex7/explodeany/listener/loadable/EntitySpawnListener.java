package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.specific.TNTSpecificEntityConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class EntitySpawnListener implements LoadableListener {
    public static final String TNT_PRIMED_LOCATION_TAG = "eany-tnt-primed-location";

    private boolean doTagPrimedTnt = false;
    private boolean doResetPrimedTntVelocity = false;

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        final EntityConfiguration tntConfiguration = ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(RegularVanillaEntityConfiguration.getConfigurationId())
                .getEntityConfigurations().get(ExplodingVanillaEntity.PRIMED_TNT);
        if (tntConfiguration == null) {
            return;
        }

        if (tntConfiguration.getSpecificConfiguration() instanceof TNTSpecificEntityConfiguration) {
            TNTSpecificEntityConfiguration specificConfig = (TNTSpecificEntityConfiguration) tntConfiguration
                    .getSpecificConfiguration();
            this.doTagPrimedTnt = specificConfig.getMinimumDistanceToDamageBlocksUnderwater() > 0.0;
            this.doResetPrimedTntVelocity = specificConfig.doSnapToBlockGridOnPriming();
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onEntitySpawn(EntitySpawnEvent event) {
        final Entity entity = event.getEntity();
        final ExplodingVanillaEntity explodingEntity = ExplodingVanillaEntity.fromEntity(entity);

        if (explodingEntity == ExplodingVanillaEntity.PRIMED_TNT) {
            if (this.doTagPrimedTnt) {
                entity.setMetadata(TNT_PRIMED_LOCATION_TAG,
                        new FixedMetadataValue(ExplodeAny.getInstance(), event.getLocation()));
            }

            if (this.doResetPrimedTntVelocity) {
                final Vector newVelocity = entity.getVelocity();
                newVelocity.setX(0);
                newVelocity.setZ(0);
                entity.setVelocity(newVelocity);
            }
        }
    }

    @Override
    public void unload() {
        EntitySpawnEvent.getHandlerList().unregister(this);
    }
}
