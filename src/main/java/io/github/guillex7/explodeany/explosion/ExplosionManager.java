package io.github.guillex7.explodeany.explosion;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.block.BlockStatus;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.configuration.section.ParticleConfiguration;
import io.github.guillex7.explodeany.configuration.section.SoundConfiguration;

public class ExplosionManager {
	private static ExplosionManager instance;

	private ExplosionManager() {
	}

	public static ExplosionManager getInstance() {
		if (instance == null) {
			instance = new ExplosionManager();
		}
		return instance;
	}

	public void removeHandledBlocksFromList(Map<Material, EntityMaterialConfiguration> materialConfigurations,
			List<Block> blockList) {
		Iterator<Block> iterator = blockList.iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			if (materialConfigurations.containsKey(block.getType())) {
				iterator.remove();
			}
		}
	}

	public boolean manageExplosion(Map<Material, EntityMaterialConfiguration> materialConfigurations,
			EntityConfiguration entityConfiguration, Location sourceLocation, int explosionRadius,
			float explosionPower) {
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
					if (squaredExplosionRadius < squaredDistance) {
						continue;
					}

					Block block = sourceLocation.getWorld().getBlockAt(x, y, z);
					EntityMaterialConfiguration materialConfiguration = materialConfigurations.get(block.getType());
					if (materialConfiguration == null) {
						continue;
					}

					damageBlock(materialConfiguration, block, sourceLocation, squaredExplosionRadius, squaredDistance);
				}
			}
		}

		SoundConfiguration soundConfiguration = entityConfiguration.getSoundConfiguration();
		if (soundConfiguration.getSound() != null) {
			sourceLocation.getWorld().playSound(sourceLocation, soundConfiguration.getSound(),
					soundConfiguration.getVolume().floatValue(), soundConfiguration.getPitch().floatValue());
		}

		ParticleConfiguration particleConfiguration = entityConfiguration.getParticleConfiguration();
		if (particleConfiguration.getParticle() != null) {
			sourceLocation.getWorld().spawnParticle(particleConfiguration.getParticle(), sourceLocation.getX(),
					sourceLocation.getY(), sourceLocation.getZ(), particleConfiguration.getAmount(),
					particleConfiguration.getDeltaX(), particleConfiguration.getDeltaY(),
					particleConfiguration.getDeltaZ(), particleConfiguration.getSpeed(),
					particleConfiguration.getOptions(), particleConfiguration.isForce());
		}
		
		if (entityConfiguration.isExplosionDamageBlocksUnderwater() && sourceLocation.getBlock().isLiquid()) {
			sourceLocation.getBlock().setType(Material.AIR);
			return sourceLocation.getWorld().createExplosion(sourceLocation,
					explosionPower * entityConfiguration.getUnderwaterExplosionFactor().floatValue());
		}

		return false;
	}

	public void damageBlock(EntityMaterialConfiguration materialConfiguration, Block targetBlock,
			Location sourceLocation, int squaredExplosionRadius, double squaredDistance) {
		final double maximumSquaredDistance = squaredExplosionRadius * materialConfiguration.getExplosionRadiusFactor();
		if (squaredDistance > maximumSquaredDistance) {
			return;
		}

		double effectiveDamage = materialConfiguration.getDamage();

		// Underwater attenuation
		if (materialConfiguration.isUnderwaterAffected()
				&& performUnderwaterDetection(materialConfiguration, sourceLocation, targetBlock.getLocation())) {
			effectiveDamage *= materialConfiguration.getUnderwaterDamageFactor();
		}

		// Linear distance attenuation
		// damageFactor = 1 - c*(d-1)/dmax if 1 <= d < dmax
		// 0 if dmax <= d
		// c = distance attenuation factor
		// d = distance from the center (always >= 1)
		// dmax = maximum distance
		// Use squared distance to avoid sqrt overhead
		effectiveDamage *= 1
				- materialConfiguration.getDistanceAttenuationFactor() * (squaredDistance - 1) / maximumSquaredDistance;

		BlockStatus affectedBlockStatus = BlockDatabase.getInstance().getBlockStatus(targetBlock);
		affectedBlockStatus.damage(effectiveDamage);
		if (affectedBlockStatus.shouldBreak()) {
			SoundConfiguration soundConfiguration = materialConfiguration.getSoundConfiguration();
			if (soundConfiguration.getSound() != null) {
				targetBlock.getWorld().playSound(targetBlock.getLocation(), soundConfiguration.getSound(),
						soundConfiguration.getVolume().floatValue(), soundConfiguration.getPitch().floatValue());
			}

			ParticleConfiguration particleConfiguration = materialConfiguration.getParticleConfiguration();
			if (particleConfiguration.getParticle() != null) {
				targetBlock.getWorld().spawnParticle(particleConfiguration.getParticle(),
						targetBlock.getLocation().getX(), targetBlock.getLocation().getY(),
						targetBlock.getLocation().getZ(), particleConfiguration.getAmount(),
						particleConfiguration.getDeltaX(), particleConfiguration.getDeltaY(),
						particleConfiguration.getDeltaZ(), particleConfiguration.getSpeed(),
						particleConfiguration.getOptions(), particleConfiguration.isForce());
			}

			if (materialConfiguration.shouldBeDropped()) {
				targetBlock.breakNaturally();
			} else {
				targetBlock.setType(Material.AIR);
			}

			BlockDatabase.getInstance().removeBlockStatus(targetBlock);
		}
	}

	private boolean performUnderwaterDetection(EntityMaterialConfiguration materialConfiguration, Location source,
			Location target) {
		return materialConfiguration.isFancyUnderwaterDetection() ? isLiquidInTrajectory(source, target)
				: source.getBlock().isLiquid();
	}

	private boolean isLiquidInTrajectory(Location source, Location target) {
		if (source.equals(target)) {
			return source.getBlock().isLiquid();
		}

		BlockIterator it = new BlockIterator(source.getWorld(), source.toVector(),
				target.toVector().subtract(source.toVector()), 0, (int) source.distance(target));
		while (it.hasNext()) {
			if (it.next().isLiquid()) {
				return true;
			}
		}
		return false;
	}
}
