package io.github.guillex7.explodeany.explosion;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.block.BlockStatus;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;

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
        final int cx = sourceLocation.getBlockX();
        final int cy = sourceLocation.getBlockY();
        final int cz = sourceLocation.getBlockZ();
        final int cxpr = cx + explosionRadius;
        final int cypr = cy + explosionRadius;
        final int czpr = cz + explosionRadius;
        final int squaredExplosionRadius = explosionRadius * explosionRadius;

        for (int x = cx - explosionRadius; x < cxpr; x++) {
            for (int y = cy - explosionRadius; y < cypr; y++) {
                for (int z = cz - explosionRadius; z < czpr; z++) {
                    final int dx = x - cx;
                    final int dy = y - cy;
                    final int dz = z - cz;
                    final int squaredDistance = dx * dx + dy * dy + dz * dz;

                    if (squaredExplosionRadius < squaredDistance) {
                        continue;
                    }

                    Block block = sourceLocation.getWorld().getBlockAt(x, y, z);
                    EntityMaterialConfiguration materialConfiguration = materialConfigurations.get(block.getType());
                    if (materialConfiguration == null) {
                        continue;
                    }

                    double effectiveSquaredExplosionRadius = squaredExplosionRadius
                            * materialConfiguration.getExplosionRadiusFactor();
                    if (squaredDistance > effectiveSquaredExplosionRadius) {
                        continue;
                    }

                    damageBlock(materialConfiguration, block, sourceLocation, effectiveSquaredExplosionRadius,
                            squaredDistance);
                }
            }
        }

        entityConfiguration.getSoundConfiguration().playAt(sourceLocation);
        entityConfiguration.getParticleConfiguration().spawnAt(sourceLocation);

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

        if (materialConfiguration.isUnderwaterAffected()
                && performUnderwaterDetection(materialConfiguration, sourceLocation, targetBlock.getLocation())) {
            effectiveDamage *= materialConfiguration.getUnderwaterDamageFactor();
        }

        effectiveDamage *= 1
                - materialConfiguration.getDistanceAttenuationFactor() * (squaredDistance - 1) / squaredExplosionRadius;

        BlockStatus affectedBlockStatus = BlockDatabase.getInstance().getBlockStatus(targetBlock);
        affectedBlockStatus.damage(effectiveDamage);
        if (affectedBlockStatus.shouldBreak()) {
            materialConfiguration.getSoundConfiguration().playAt(targetBlock.getLocation());
            materialConfiguration.getParticleConfiguration().spawnAt(targetBlock.getLocation());

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
}
