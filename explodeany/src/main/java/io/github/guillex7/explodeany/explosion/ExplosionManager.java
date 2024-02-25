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
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.block.BlockStatus;
import io.github.guillex7.explodeany.compat.common.api.IBlockDataUtils;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.section.EntityBehavioralConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.drop.DropCollector;
import io.github.guillex7.explodeany.explosion.liquid.BlockLiquidDetector;
import io.github.guillex7.explodeany.explosion.liquid.TrajectoryExplosionLiquidDetector;

public class ExplosionManager {
    private static ExplosionManager instance;

    public static final String EXPLOSION_MANAGER_SPAWNED_TAG = "eany-em-spawned";
    public static final String EXPLOSION_MANAGER_MATERIAL_CONFIGURATIONS_TAG = "eany-em-material-configurations";

    private final BlockLiquidDetector blockLiquidDetector;
    private final TrajectoryExplosionLiquidDetector trajectoryExplosionWaterDetector;
    private final IBlockDataUtils blockDataUtils;
    private final BlockDatabase blockDatabase;

    private ExplosionManager() {
        this.blockLiquidDetector = new BlockLiquidDetector();
        this.trajectoryExplosionWaterDetector = new TrajectoryExplosionLiquidDetector();
        this.blockDataUtils = CompatibilityManager.getInstance().getApi().getBlockDataUtils();
        this.blockDatabase = BlockDatabase.getInstance();
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
            EntityConfiguration entityConfiguration, Location sourceLocation, double originalRawExplosionRadius) {
        final boolean isSourceLocationLiquid = sourceLocation.getBlock().isLiquid();
        final boolean isSourceLocationLiquidlike = this.blockLiquidDetector.isBlockLiquidlike(sourceLocation);

        double rawExplosionRadius = entityConfiguration.getExplosionRadius() != 0d
                ? entityConfiguration.getExplosionRadius()
                : originalRawExplosionRadius;

        if (isSourceLocationLiquidlike) {
            rawExplosionRadius *= entityConfiguration.getUnderwaterExplosionFactor();
        } else {
            rawExplosionRadius *= entityConfiguration.getExplosionFactor();
        }

        final int explosionRadius = (int) rawExplosionRadius;
        final int cx = sourceLocation.getBlockX();
        final int cy = sourceLocation.getBlockY();
        final int cz = sourceLocation.getBlockZ();
        final int cxpr = cx + explosionRadius;
        final int cypr = cy + explosionRadius;
        final int czpr = cz + explosionRadius;
        final int squaredExplosionRadius = explosionRadius * explosionRadius;
        final World sourceWorld = sourceLocation.getWorld();

        final EntityBehavioralConfiguration entityBehavioralConfiguration = entityConfiguration
                .getEntityBehavioralConfiguration();
        final List<Block> unhandledWaterloggedBlocks = new ArrayList<>(squaredExplosionRadius);
        final List<Block> liquidBlocks = new ArrayList<>(squaredExplosionRadius);
        final Map<Material, Integer> packedDropCounter = new HashMap<>(materialConfigurations.size(), 1);

        final DropCollector dropCollector = entityConfiguration.doPackDroppedItems()
                ? (material, location) -> {
                    packedDropCounter.put(material, packedDropCounter.getOrDefault(material, 0) + 1);
                }
                : (material, location) -> {
                    sourceWorld.dropItemNaturally(location, new ItemStack(material, 1));
                };

        // Hint: This is a very expensive operation, so we only do it if we have to.
        if (!materialConfigurations.isEmpty() || entityBehavioralConfiguration.doesExplosionRemoveNearbyLiquids()
                || entityBehavioralConfiguration.doesExplosionRemoveWaterloggedStateFromNearbyBlocks()
                || entityBehavioralConfiguration.doesExplosionRemoveNearbyWaterloggedBlocks()) {
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
                            if (this.blockDataUtils.isBlockWaterlogged(block)) {
                                unhandledWaterloggedBlocks.add(block);
                            } else if (block.isLiquid()) {
                                liquidBlocks.add(block);
                            }
                            continue;
                        }

                        this.damageBlock(materialConfiguration, block, sourceLocation,
                                squaredExplosionRadius,
                                squaredDistance, isSourceLocationLiquidlike, dropCollector);
                    }
                }
            }
        }

        if (entityConfiguration.doPackDroppedItems()) {
            for (Map.Entry<Material, Integer> entry : packedDropCounter.entrySet()) {
                sourceWorld.dropItemNaturally(sourceLocation, new ItemStack(entry.getKey(), entry.getValue()));
            }
        }

        entityConfiguration.getSoundConfiguration().playAt(sourceLocation);
        entityConfiguration.getParticleConfiguration().spawnAt(sourceLocation);

        if (entityBehavioralConfiguration.doesExplosionRemoveNearbyWaterloggedBlocks()
                && ((entityBehavioralConfiguration.doesExplosionRemoveNearbyWaterloggedBlocksSurface()
                        && !isSourceLocationLiquidlike)
                        || (entityBehavioralConfiguration.doesExplosionRemoveNearbyWaterloggedBlocksUnderwater()
                                && isSourceLocationLiquidlike))) {
            for (Block unhandledWaterloggedBlock : unhandledWaterloggedBlocks) {
                unhandledWaterloggedBlock.setType(Material.AIR);
            }
        } else if (entityBehavioralConfiguration.doesExplosionRemoveWaterloggedStateFromNearbyBlocks() &&
                ((entityBehavioralConfiguration.doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface()
                        && !isSourceLocationLiquidlike)
                        || (entityBehavioralConfiguration
                                .doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater()
                                && isSourceLocationLiquidlike))) {
            for (Block unhandledWaterloggedBlock : unhandledWaterloggedBlocks) {
                this.blockDataUtils.setIsBlockWaterlogged(unhandledWaterloggedBlock, false);
            }
        }

        if (entityBehavioralConfiguration.doesExplosionRemoveNearbyLiquids()
                && ((entityBehavioralConfiguration.doesExplosionRemoveNearbyLiquidsSurface()
                        && !isSourceLocationLiquidlike)
                        || (entityBehavioralConfiguration.doesExplosionRemoveNearbyLiquidsUnderwater()
                                && isSourceLocationLiquidlike))) {
            for (Block liquidBlock : liquidBlocks) {
                liquidBlock.setType(Material.AIR);
            }
        }

        if (entityConfiguration.doesExplosionDamageBlocksUnderwater()) {
            if (isSourceLocationLiquid) {
                sourceLocation.getBlock().setType(Material.AIR);
                this.spawnManagedExplosion(sourceLocation, materialConfigurations, rawExplosionRadius);
                return entityConfiguration.doReplaceOriginalExplosionWhenUnderwater();
            } else if (isSourceLocationLiquidlike) {
                this.blockDataUtils.setIsBlockWaterlogged(sourceLocation.getBlock(), false);
                this.spawnManagedExplosion(sourceLocation, materialConfigurations, rawExplosionRadius);
                return entityConfiguration.doReplaceOriginalExplosionWhenUnderwater();
            }
        }

        if (entityConfiguration.doReplaceOriginalExplosion()) {
            this.spawnManagedExplosion(sourceLocation, materialConfigurations, rawExplosionRadius);
            return true;
        }

        return false;
    }

    private void spawnManagedExplosion(Location location,
            Map<Material, EntityMaterialConfiguration> materialConfigurations, double explosionRadius) {
        TNTPrimed explosiveEntity = (TNTPrimed) location.getWorld().spawn(location, TNTPrimed.class);
        this.markEntityAsSpawnedByExplosionManager(explosiveEntity, materialConfigurations);
        explosiveEntity.setFuseTicks(0);
        explosiveEntity.setYield((float) explosionRadius);
    }

    private void damageBlock(EntityMaterialConfiguration materialConfiguration, Block targetBlock,
            Location sourceLocation, double squaredExplosionRadius,
            double squaredDistance,
            boolean isSourceLocationLiquidlike, DropCollector dropCollector) {
        final Location targetLocation = targetBlock.getLocation();
        double effectiveDamage = materialConfiguration.getDamage();

        if (materialConfiguration.isUnderwaterAffected()
                && this.areUnderwaterRulesApplicable(materialConfiguration, sourceLocation,
                        targetLocation,
                        isSourceLocationLiquidlike)) {
            effectiveDamage *= materialConfiguration.getUnderwaterDamageFactor();
        }

        effectiveDamage *= 1
                - materialConfiguration.getDistanceAttenuationFactor() * (squaredDistance - 1) / squaredExplosionRadius;

        final BlockStatus affectedBlockStatus = this.blockDatabase.getBlockStatus(targetBlock);
        affectedBlockStatus.damage(effectiveDamage);

        if (affectedBlockStatus.shouldBreak()) {
            materialConfiguration.getSoundConfiguration().playAt(targetLocation);
            materialConfiguration.getParticleConfiguration().spawnAt(targetLocation);

            final Material targetBlockMaterial = targetBlock.getType();
            targetBlock.setType(Material.AIR);
            this.blockDatabase.removeBlockStatus(targetBlock);
            if (materialConfiguration.shouldBeDropped()) {
                dropCollector.collect(targetBlockMaterial, targetLocation);
            }
        }
    }

    private boolean areUnderwaterRulesApplicable(EntityMaterialConfiguration materialConfiguration,
            Location sourceLocation,
            Location targetLocation,
            boolean isSourceLocationLiquidlike) {
        return materialConfiguration.isFancyUnderwaterDetection()
                ? this.trajectoryExplosionWaterDetector.isLiquidInTrajectory(sourceLocation, targetLocation)
                : isSourceLocationLiquidlike;
    }
}
