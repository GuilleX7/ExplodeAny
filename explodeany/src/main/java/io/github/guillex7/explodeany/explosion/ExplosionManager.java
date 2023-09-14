package io.github.guillex7.explodeany.explosion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.block.BlockStatus;
import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.liquid.BlockLiquidDetector;
import io.github.guillex7.explodeany.explosion.liquid.TrajectoryExplosionLiquidDetector;

public class ExplosionManager {
    private static ExplosionManager instance;

    public static final String EXPLOSION_MANAGER_SPAWNED_TAG = "eany-em-spawned";
    public static final String EXPLOSION_MANAGER_MATERIAL_CONFIGURATIONS_TAG = "eany-em-material-configurations";

    private final BlockLiquidDetector blockLiquidDetector;
    private final TrajectoryExplosionLiquidDetector trajectoryExplosionWaterDetector;
    private final IBlockDataUtils blockDataUtils;

    private ExplosionManager() {
        this.blockLiquidDetector = new BlockLiquidDetector();
        this.trajectoryExplosionWaterDetector = new TrajectoryExplosionLiquidDetector();
        this.blockDataUtils = CompatibilityManager.getInstance().getApi().getBlockDataUtils();
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

    private void markEntityAsSpawnedByExplosionManager(Entity entity,
            Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        entity.setMetadata(EXPLOSION_MANAGER_SPAWNED_TAG,
                new FixedMetadataValue(ExplodeAny.getInstance(), true));
        entity.setMetadata(EXPLOSION_MANAGER_MATERIAL_CONFIGURATIONS_TAG,
                new FixedMetadataValue(ExplodeAny.getInstance(), materialConfigurations));
    }

    public boolean isEntitySpawnedByExplosionManager(Entity entity) {
        return entity.hasMetadata(EXPLOSION_MANAGER_SPAWNED_TAG);
    }

    @SuppressWarnings("unchecked")
    public Map<Material, EntityMaterialConfiguration> getMaterialConfigurationsFromEntity(Entity entity) {
        List<MetadataValue> metadataValueList = entity.getMetadata(EXPLOSION_MANAGER_MATERIAL_CONFIGURATIONS_TAG);
        
        if (!metadataValueList.isEmpty()) {
            return (Map<Material, EntityMaterialConfiguration>) metadataValueList.get(0).value();
        } else {
            return new HashMap<>(0, 0);
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
        final World sourceWorld = sourceLocation.getWorld();

        final List<Block> unhandledWaterloggedBlocks = new ArrayList<>(256);
        final List<Block> liquidBlocks = new ArrayList<>(256);

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

                    Block block = sourceWorld.getBlockAt(x, y, z);
                    if (block.isEmpty()) {
                        continue;
                    }

                    EntityMaterialConfiguration materialConfiguration = materialConfigurations.get(block.getType());
                    if (materialConfiguration == null) {
                        if (blockDataUtils.isBlockWaterlogged(block)) {
                            unhandledWaterloggedBlocks.add(block);
                        } else if (block.isLiquid()) {
                            liquidBlocks.add(block);
                        }
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

        final boolean isSourceLocationUnderwater = sourceLocation.getBlock().isLiquid();

        if (entityConfiguration.doesExplosionRemoveWaterloggedStateFromNearbyBlocks()) {
            for (Block unhandledWaterloggedBlock : unhandledWaterloggedBlocks) {
                blockDataUtils.setIsBlockWaterlogged(unhandledWaterloggedBlock, false);
            }
        }

        if (entityConfiguration.doesExplosionRemoveNearbyLiquid()) {
            for (Block liquidBlock : liquidBlocks) {
                liquidBlock.setType(Material.AIR);
            }
        }

        if (entityConfiguration.doesExplosionDamageBlocksUnderwater()
                && isSourceLocationUnderwater) {
            sourceLocation.getBlock().setType(Material.AIR);

            TNTPrimed explosiveEntity = (TNTPrimed) sourceWorld.spawn(sourceLocation, TNTPrimed.class);
            markEntityAsSpawnedByExplosionManager(explosiveEntity, materialConfigurations);
            explosiveEntity.setFuseTicks(0);
            explosiveEntity.setYield(explosionPower *
                    entityConfiguration.getUnderwaterExplosionFactor().floatValue());

            return entityConfiguration.doReplaceOriginalExplosionWhenUnderwater();
        }

        return false;
    }

    private void damageBlock(EntityMaterialConfiguration materialConfiguration, Block targetBlock,
            Location sourceLocation, double squaredExplosionRadius, double squaredDistance) {
        double effectiveDamage = materialConfiguration.getDamage();

        if (materialConfiguration.isUnderwaterAffected()
                && areUnderwaterRulesApplicable(materialConfiguration, sourceLocation, targetBlock.getLocation())) {
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

    private boolean areUnderwaterRulesApplicable(EntityMaterialConfiguration materialConfiguration,
            Location sourceLocation,
            Location targetLocation) {
        return materialConfiguration.isFancyUnderwaterDetection()
                ? trajectoryExplosionWaterDetector.isLiquidInTrajectory(sourceLocation, targetLocation)
                : blockLiquidDetector.isBlockLiquidlike(sourceLocation);
    }
}