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

					final double effectiveSquaredExplosionRadius = squaredExplosionRadius
							* materialConfiguration.getExplosionRadiusFactor();
					if (squaredDistance > effectiveSquaredExplosionRadius) {
						continue;
					}

					damageBlock(materialConfiguration, block, sourceLocation, effectiveSquaredExplosionRadius,
							squaredDistance);
				}
			}
		}

		SoundConfiguration soundConfiguration = entityConfiguration.getSoundConfiguration();
		if (soundConfiguration.getSound() != null) {
			playSound(soundConfiguration, sourceLocation);
		}

		ParticleConfiguration particleConfiguration = entityConfiguration.getParticleConfiguration();
		if (particleConfiguration.getParticle() != null) {
			spawnParticles(particleConfiguration, sourceLocation);
		}

		if (entityConfiguration.isExplosionDamageBlocksUnderwater() && sourceLocation.getBlock().isLiquid()) {
			sourceLocation.getBlock().setType(Material.AIR);
			return sourceLocation.getWorld().createExplosion(sourceLocation,
					explosionPower * entityConfiguration.getUnderwaterExplosionFactor().floatValue());
		}

		return false;
	}

	public void damageBlock(EntityMaterialConfiguration materialConfiguration, Block targetBlock,
			Location sourceLocation, double squaredExplosionRadius, double squaredDistance) {
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
		// dmax = explosion radius
		effectiveDamage *= 1
				- materialConfiguration.getDistanceAttenuationFactor() * (squaredDistance - 1) / squaredExplosionRadius;

		BlockStatus affectedBlockStatus = BlockDatabase.getInstance().getBlockStatus(targetBlock);
		affectedBlockStatus.damage(effectiveDamage);
		if (affectedBlockStatus.shouldBreak()) {
			SoundConfiguration soundConfiguration = materialConfiguration.getSoundConfiguration();
			if (soundConfiguration.getSound() != null) {
				playSound(soundConfiguration, targetBlock.getLocation());
			}

			ParticleConfiguration particleConfiguration = materialConfiguration.getParticleConfiguration();
			if (particleConfiguration.getParticle() != null) {
				spawnParticles(particleConfiguration, targetBlock.getLocation());
			}

			if (materialConfiguration.shouldBeDropped()) {
				targetBlock.breakNaturally();
			} else {
				targetBlock.setType(Material.AIR);
			}

			BlockDatabase.getInstance().removeBlockStatus(targetBlock);
		}
	}

	private boolean performUnderwaterDetection(EntityMaterialConfiguration materialConfiguration,
			Location sourceLocation,
			Location targetLocation) {
		return materialConfiguration.isFancyUnderwaterDetection() ? isLiquidInTrajectory(sourceLocation, targetLocation)
				: sourceLocation.getBlock().isLiquid();
	}

	private boolean isLiquidInTrajectory(Location sourceLocation, Location targetLocation) {
		if (sourceLocation.equals(targetLocation)) {
			return sourceLocation.getBlock().isLiquid();
		}

		BlockIterator iterator = new BlockIterator(sourceLocation.getWorld(), sourceLocation.toVector(),
				targetLocation.toVector().subtract(sourceLocation.toVector()), 0,
				(int) sourceLocation.distance(targetLocation));
		while (iterator.hasNext()) {
			if (iterator.next().isLiquid()) {
				return true;
			}
		}
		return false;
	}

	private void spawnParticles(ParticleConfiguration particleConfiguration, Location location) {
		location.getWorld().spawnParticle(particleConfiguration.getParticle(), location.getX(), location.getY(),
				location.getZ(),
				particleConfiguration.getAmount(), particleConfiguration.getDeltaX(), particleConfiguration.getDeltaY(),
				particleConfiguration.getDeltaZ(), particleConfiguration.getSpeed(), particleConfiguration.getOptions(),
				particleConfiguration.isForce());
	}

	private void playSound(SoundConfiguration soundConfiguration, Location location) {
		location.getWorld().playSound(location, soundConfiguration.getSound(),
				soundConfiguration.getVolume().floatValue(),
				soundConfiguration.getPitch().floatValue());
	}
}
