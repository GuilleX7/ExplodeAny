package io.github.guillex7.explodeany.explosion;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.block.BlockStatus;
import io.github.guillex7.explodeany.configuration.EntityMaterialConfiguration;

public class ExplosionManager {
	public static void removeHandledBlocksFromList(Map<Material, EntityMaterialConfiguration> materialConfigurations,
			List<Block> blockList) {
		Iterator<Block> iterator = blockList.iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			if (materialConfigurations.containsKey(block.getType())) {
				iterator.remove();
			}
		}
	}
	
	public static void manageExplosion(Map<Material, EntityMaterialConfiguration> materialConfigurations,
			Location sourceLocation, int explosionRadius) {
		int cx = sourceLocation.getBlockX();
		int cy = sourceLocation.getBlockY();
		int cz = sourceLocation.getBlockZ();
		Vector sourceVector = new Vector(cx, cy, cz);
		int cxpr = cx + explosionRadius;
		int cypr = cy + explosionRadius;
		int czpr = cz + explosionRadius;
		int squaredExplosionRadius = explosionRadius * explosionRadius;
		
		for (int x = cx - explosionRadius; x < cxpr; x++) {
			for (int y = cy - explosionRadius; y < cypr; y++) {
				for (int z = cz - explosionRadius; z < czpr; z++) {
					double squaredDistance = sourceVector.distanceSquared(new Vector(x, y, z));
					if (squaredExplosionRadius >= squaredDistance) {
						Block block = sourceLocation.getWorld().getBlockAt(x, y, z);
						tryDamageBlock(materialConfigurations, block, sourceLocation, squaredExplosionRadius, squaredDistance);
					}
				}
			}
		}
	}

	public static void tryDamageBlock(Map<Material, EntityMaterialConfiguration> materialConfigurations, Block targetBlock,
			Location sourceLocation, int squaredExplosionRadius, double squaredDistance) {
		EntityMaterialConfiguration materialConfiguration = materialConfigurations.get(targetBlock.getType());
		if (materialConfiguration == null) {
			return;
		}
		
		double effectiveDamage = materialConfiguration.getDamage();
		
		// Underwater attenuation
		if (materialConfiguration.isUnderwaterAffected() &&
				isLiquidInLocation(sourceLocation)) {
			effectiveDamage *= materialConfiguration.getUnderwaterDamageFactor();
		}
		
		// Linear distance attenuation
		// damageFactor = 1 - c*(d-1)/dmax 	if 1 <= d < dmax
		// 				  0 		   		if dmax <= d     
		// c = distance attenuation factor
		// d = distance from the center (always >= 1)
		// dmax = maximum distance
		// Use squared distance to avoid sqrt overhead
		double maximumSquaredDistance = squaredExplosionRadius * materialConfiguration.getExplosionRadiusFactor();
		if (squaredDistance > maximumSquaredDistance) {
			return;
		}
		effectiveDamage *=
				1 - materialConfiguration.getDistanceAttenuationFactor() *
				(squaredDistance - 1) / maximumSquaredDistance;

		BlockStatus affectedBlockStatus = BlockDatabase.getInstance().getBlockStatus(targetBlock);
		affectedBlockStatus.damage(effectiveDamage);
		if (affectedBlockStatus.shouldBreak()) {
			if (materialConfiguration.shouldBeDropped()) {
				targetBlock.breakNaturally();
			} else {
				targetBlock.setType(Material.AIR);
			}
			BlockDatabase.getInstance().removeBlockStatus(targetBlock);
		}
	}
	
	public static boolean isLiquidInLocation(Location location) {
		return location.getBlock().isLiquid();
	}
}
