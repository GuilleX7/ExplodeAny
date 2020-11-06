package io.github.guillex7.explodeany.listener.loadable;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import at.pavlov.cannons.event.ProjectileImpactEvent;
import at.pavlov.cannons.event.ProjectilePiercingEvent;
import io.github.guillex7.explodeany.configuration.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;

public final class CannonExplosionListener implements LoadableExplosionListener {
	private static CannonExplosionListener instance;
	
	private CannonExplosionListener() {}
	
	public static CannonExplosionListener getInstance() {
		if (instance == null) {
			instance = new CannonExplosionListener();
		}
		return instance;
	}

	@Override
	public String getName() {
		return "Cannons";
	}
	
	@Override
	public boolean shouldBeLoaded() {
		return CannonProjectileConfiguration.getInstance().shouldBeLoaded();
	}
	
	@EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
	public void onProjectileImpact(ProjectileImpactEvent event) {
		if (event.getProjectile() == null) {
			return;
		}
		
		Map<Material, EntityMaterialConfiguration> materialConfigurations = CannonProjectileConfiguration.getInstance()
				.getEntityMaterialConfigurations().get(event.getProjectile().getProjectileId());
		if (materialConfigurations == null) {
			return;
		}
		
		ExplosionManager.manageExplosion(materialConfigurations, event.getImpactLocation(),
				(int) event.getProjectile().getExplosionPower());
	}
	
	@EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
	public void onProjectilePiercing(ProjectilePiercingEvent event) {
		if (event.getProjectile() == null) {
			return;
		}
		
		Map<Material, EntityMaterialConfiguration> materialConfigurations = CannonProjectileConfiguration.getInstance()
				.getEntityMaterialConfigurations().get(event.getProjectile().getProjectileId());
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
