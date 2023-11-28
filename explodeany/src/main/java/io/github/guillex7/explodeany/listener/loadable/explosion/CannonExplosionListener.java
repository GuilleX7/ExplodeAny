package io.github.guillex7.explodeany.listener.loadable.explosion;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import at.pavlov.cannons.event.ProjectileImpactEvent;
import at.pavlov.cannons.event.ProjectilePiercingEvent;
import at.pavlov.cannons.projectile.Projectile;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;

public final class CannonExplosionListener extends BaseExplosionListener {
    @Override
    public String getName() {
        return "Cannons explosions";
    }

    @Override
    public boolean isAnnounceable() {
        return true;
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
    public void onProjectileImpact(ProjectileImpactEvent event) {
        if (ConfigurationManager.getInstance().getDisabledWorlds()
                .contains(event.getImpactLocation().getWorld().getName())) {
            return;
        }

        Projectile projectile = event.getProjectile();
        if (projectile == null) {
            return;
        }

        String projectileId = event.getProjectile().getProjectileId();

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(projectileId);
        if (materialConfigurations == null) {
            return;
        }

        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(projectileId);

        double explosionPower = projectile.getExplosionPower();
        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getImpactLocation(), explosionPower)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onProjectilePiercing(ProjectilePiercingEvent event) {
        if (ConfigurationManager.getInstance().getDisabledWorlds()
                .contains(event.getImpactLocation().getWorld().getName())) {
            return;
        }

        Projectile projectile = event.getProjectile();
        if (projectile == null) {
            return;
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations()
                .get(projectile.getProjectileId());
        if (materialConfigurations == null) {
            return;
        }

        Iterator<Block> iterator = event.getBlockList().iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
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

    @Override
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance()
                .getRegisteredEntityConfiguration(CannonProjectileConfiguration.getConfigurationId());
    }
}
