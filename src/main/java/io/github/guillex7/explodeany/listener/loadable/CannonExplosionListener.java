package io.github.guillex7.explodeany.listener.loadable;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import at.pavlov.cannons.event.ProjectileImpactEvent;
import at.pavlov.cannons.event.ProjectilePiercingEvent;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableSectionConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;

public final class CannonExplosionListener implements LoadableListener {
	@Override
	public String getName() {
		return "Cannons explosions";
	}

	@Override
	public boolean shouldBeLoaded() {
		return ConfigurationManager.getInstance().getRegisteredEntityConfiguration("CannonProjectile").shouldBeLoaded();
	}

	@Override
	public boolean isAdvisable() {
		return true;
	}

	@EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
	public void onProjectileImpact(ProjectileImpactEvent event) {
		if (event.getProjectile() == null) {
			return;
		}
		String projectileId = event.getProjectile().getProjectileId();

		LoadableSectionConfiguration<?> cannonProjectileConfiguration = ConfigurationManager.getInstance()
				.getRegisteredEntityConfiguration("CannonProjectile");

		Map<Material, EntityMaterialConfiguration> materialConfigurations = cannonProjectileConfiguration
				.getEntityMaterialConfigurations().get(projectileId);
		if (materialConfigurations == null) {
			return;
		}

		EntityConfiguration entityConfiguration = cannonProjectileConfiguration.getEntityConfigurations()
				.get(projectileId);

		float explosionPower = event.getProjectile().getExplosionPower();
		if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
				event.getImpactLocation(), (int) explosionPower, explosionPower)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
	public void onProjectilePiercing(ProjectilePiercingEvent event) {
		if (event.getProjectile() == null) {
			return;
		}

		Map<Material, EntityMaterialConfiguration> materialConfigurations = ConfigurationManager.getInstance()
				.getRegisteredEntityConfiguration("CannonProjectile").getEntityMaterialConfigurations()
				.get(event.getProjectile().getProjectileId());
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

		// Remove all blocks after the first handled block
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
