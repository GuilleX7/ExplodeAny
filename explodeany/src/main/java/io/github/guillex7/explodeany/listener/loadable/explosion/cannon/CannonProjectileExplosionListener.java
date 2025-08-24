package io.github.guillex7.explodeany.listener.loadable.explosion.cannon;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import at.pavlov.cannons.event.ProjectileImpactEvent;
import at.pavlov.cannons.event.ProjectilePiercingEvent;
import at.pavlov.cannons.projectile.Projectile;
import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.cannon.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public final class CannonProjectileExplosionListener implements LoadableListener {
    private CannonProjectileConfiguration configuration;

    @Override
    public boolean shouldBeLoaded() {
        return ConfigurationManager.getInstance()
                .isConfigurationSectionLoaded(CannonProjectileConfiguration.getConfigurationId());
    }

    @Override
    public void load() {
        this.configuration = (CannonProjectileConfiguration) ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(CannonProjectileConfiguration.getConfigurationId());
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
    public void onProjectileImpact(final ProjectileImpactEvent event) {
        if (event.isCancelled() || ConfigurationManager.getInstance().getDisabledWorlds()
                .contains(event.getImpactLocation().getWorld().getName())) {
            return;
        }

        final Projectile projectile = event.getProjectile();
        if (projectile == null) {
            return;
        }

        final String projectileId = event.getProjectile().getProjectileId();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO,
                    "Detected Cannons projectile explosion. Projectile ID: {0}", projectileId);
        }

        final Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(projectileId);
        final EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(projectileId);

        if (materialConfigurations == null || entityConfiguration == null) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getImpactLocation(), projectile.getExplosionPower())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onProjectilePiercing(final ProjectilePiercingEvent event) {
        if (ConfigurationManager.getInstance().getDisabledWorlds()
                .contains(event.getImpactLocation().getWorld().getName())) {
            return;
        }

        final Projectile projectile = event.getProjectile();
        if (projectile == null) {
            return;
        }

        final Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations()
                .get(projectile.getProjectileId());
        if (materialConfigurations == null) {
            return;
        }

        final Iterator<Block> iterator = event.getBlockList().iterator();
        while (iterator.hasNext()) {
            final Block block = iterator.next();
            if (materialConfigurations.containsKey(block.getType())) {
                // First handled block, stop here
                event.setImpactLocation(block.getLocation());
                iterator.remove();
                break;
            }
        }

        // Prevent all blocks behind the first handled block from being destroyed
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    @Override
    public void unload() {
        ProjectileImpactEvent.getHandlerList().unregister(this);
        ProjectilePiercingEvent.getHandlerList().unregister(this);
    }
}
