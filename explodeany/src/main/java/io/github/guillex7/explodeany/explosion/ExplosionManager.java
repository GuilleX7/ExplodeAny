package io.github.guillex7.explodeany.explosion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
import io.github.guillex7.explodeany.configuration.section.EntityBehavioralConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.drop.DropCollector;
import io.github.guillex7.explodeany.explosion.drop.UnpackedDropCollector;
import io.github.guillex7.explodeany.explosion.drop.PackedDropCollector;
import io.github.guillex7.explodeany.explosion.liquid.BlockLiquidDetector;
import io.github.guillex7.explodeany.explosion.liquid.TrajectoryExplosionLiquidDetector;
import io.github.guillex7.explodeany.explosion.metadata.ExplosionMetadata;

public class ExplosionManager {
    private static ExplosionManager instance;

    public static final String EXPLOSION_MANAGER_SPAWNED_TAG = "eany-em-spawned";
    public static final String EXPLOSION_MANAGER_EXPLOSION_METADATA_TAG = "eany-em-explosion-metadata";

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

    private void attachExplosionManagerMetadataToEntity(Entity entity,
            Map<Material, EntityMaterialConfiguration> materialConfigurations, DropCollector dropCollector) {
        entity.setMetadata(EXPLOSION_MANAGER_SPAWNED_TAG,
                new FixedMetadataValue(ExplodeAny.getInstance(), true));
        entity.setMetadata(EXPLOSION_MANAGER_EXPLOSION_METADATA_TAG,
                new FixedMetadataValue(ExplodeAny.getInstance(),
                        new ExplosionMetadata(materialConfigurations, dropCollector)));
    }

    public boolean isEntitySpawnedByExplosionManager(Entity entity) {
        return entity.hasMetadata(EXPLOSION_MANAGER_SPAWNED_TAG);
    }

    public ExplosionMetadata getExplosionManagerMetadataFromEntity(Entity entity) {
        List<MetadataValue> metadataValueList = entity.getMetadata(EXPLOSION_MANAGER_EXPLOSION_METADATA_TAG);

        if (!metadataValueList.isEmpty()) {
            return (ExplosionMetadata) metadataValueList.get(0).value();
        } else {
            // This should never happen, but just in case.
            return new ExplosionMetadata(new HashMap<>(), new UnpackedDropCollector());
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
        final Location sourceBlockLocation = new Location(sourceWorld, cx, cy, cz);

        final EntityBehavioralConfiguration entityBehavioralConfiguration = entityConfiguration
                .getEntityBehavioralConfiguration();

        final Consumer<Block> waterloggedBlockConsumer = this.getWaterloggedBlockConsumer(
                entityBehavioralConfiguration,
                isSourceLocationLiquidlike);
        final Consumer<Block> liquidBlockConsumer = this.getLiquidBlockConsumer(entityBehavioralConfiguration,
                isSourceLocationLiquidlike);
        final DropCollector dropCollector = this.getDropCollector(entityConfiguration, materialConfigurations);

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

                        EntityMaterialConfiguration materialConfiguration = materialConfigurations
                                .get(block.getType());
                        if (materialConfiguration == null) {
                            if (this.blockDataUtils.isBlockWaterlogged(block)) {
                                waterloggedBlockConsumer.accept(block);
                            } else if (block.isLiquid()) {
                                liquidBlockConsumer.accept(block);
                            }
                            continue;
                        }

                        this.damageBlock(materialConfiguration, block, sourceBlockLocation,
                                explosionRadius, squaredExplosionRadius,
                                squaredDistance, isSourceLocationLiquidlike, dropCollector);
                    }
                }
            }
        }

        entityConfiguration.getSoundConfiguration().playAt(sourceLocation);
        entityConfiguration.getParticleConfiguration().spawnAt(sourceLocation);

        if (entityConfiguration.doesExplosionDamageBlocksUnderwater() && isSourceLocationLiquidlike) {
            if (isSourceLocationLiquid) {
                sourceLocation.getBlock().setType(Material.AIR);
            } else {
                this.blockDataUtils.setIsBlockWaterlogged(sourceLocation.getBlock(), false);
            }

            this.spawnManagedExplosion(sourceLocation, materialConfigurations, rawExplosionRadius,
                    dropCollector);
            return entityConfiguration.doReplaceOriginalExplosionWhenUnderwater();
        }

        if (entityConfiguration.doReplaceOriginalExplosion()) {
            this.spawnManagedExplosion(sourceLocation, materialConfigurations, rawExplosionRadius, dropCollector);
            return true;
        }

        dropCollector.dropCollectedItems(sourceBlockLocation);

        return false;
    }

    private void spawnManagedExplosion(Location location,
            Map<Material, EntityMaterialConfiguration> materialConfigurations, double explosionRadius,
            DropCollector dropCollector) {
        TNTPrimed explosiveEntity = (TNTPrimed) location.getWorld().spawn(location, TNTPrimed.class);
        this.attachExplosionManagerMetadataToEntity(explosiveEntity, materialConfigurations, dropCollector);
        explosiveEntity.setFuseTicks(0);
        explosiveEntity.setYield((float) explosionRadius);
    }

    private void damageBlock(EntityMaterialConfiguration materialConfiguration, Block targetBlock,
            Location sourceBlockLocation, int explosionRadius, double squaredExplosionRadius,
            double squaredDistance,
            boolean isSourceLocationLiquidlike, DropCollector dropCollector) {
        final Location targetBlockLocation = targetBlock.getLocation();

        double effectiveDamage = materialConfiguration.getDamage();

        if (materialConfiguration.isUnderwaterAffected()
                && this.areUnderwaterRulesApplicable(materialConfiguration, sourceBlockLocation,
                        targetBlockLocation,
                        isSourceLocationLiquidlike, explosionRadius)) {
            effectiveDamage *= materialConfiguration.getUnderwaterDamageFactor();
        }

        effectiveDamage *= 1
                - materialConfiguration.getDistanceAttenuationFactor() * (squaredDistance - 1) / squaredExplosionRadius;

        final BlockStatus affectedBlockStatus = this.blockDatabase.getBlockStatus(targetBlock);
        affectedBlockStatus.damage(effectiveDamage);

        if (affectedBlockStatus.shouldBreak()) {
            materialConfiguration.getSoundConfiguration().playAt(targetBlockLocation);
            materialConfiguration.getParticleConfiguration().spawnAt(targetBlockLocation);

            final Material targetBlockMaterial = targetBlock.getType();
            targetBlock.setType(Material.AIR);
            this.blockDatabase.removeBlockStatus(targetBlock);
            if (materialConfiguration.shouldBeDropped()) {
                dropCollector.collect(targetBlockMaterial, targetBlockLocation);
            }
        }
    }

    private boolean areUnderwaterRulesApplicable(EntityMaterialConfiguration materialConfiguration,
            Location sourceBlockLocation,
            Location targetBlockLocation,
            boolean isSourceLocationLiquidlike, int explosionRadius) {
        return materialConfiguration.isFancyUnderwaterDetection()
                ? this.trajectoryExplosionWaterDetector.isLiquidInTrajectory(sourceBlockLocation, targetBlockLocation,
                        explosionRadius)
                : isSourceLocationLiquidlike;
    }

    private Consumer<Block> getWaterloggedBlockConsumer(EntityBehavioralConfiguration entityBehavioralConfiguration,
            boolean isSourceLocationLiquidlike) {
        final boolean doesExplosionRemoveNearbyWaterloggedBlocksSurface = entityBehavioralConfiguration
                .doesExplosionRemoveNearbyWaterloggedBlocksSurface() && !isSourceLocationLiquidlike;
        final boolean doesExplosionRemoveNearbyWaterloggedBlocksUnderwater = entityBehavioralConfiguration
                .doesExplosionRemoveNearbyWaterloggedBlocksUnderwater() && isSourceLocationLiquidlike;
        final boolean doesExplosionRemoveNearbyWaterloggedBlocks = entityBehavioralConfiguration
                .doesExplosionRemoveNearbyWaterloggedBlocks()
                && (doesExplosionRemoveNearbyWaterloggedBlocksSurface
                        || doesExplosionRemoveNearbyWaterloggedBlocksUnderwater);

        final boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface = entityBehavioralConfiguration
                .doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface() && !isSourceLocationLiquidlike;
        final boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater = entityBehavioralConfiguration
                .doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater() && isSourceLocationLiquidlike;
        final boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocks = entityBehavioralConfiguration
                .doesExplosionRemoveWaterloggedStateFromNearbyBlocks()
                && (doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface
                        || doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater);

        final Consumer<Block> waterloggedBlockConsumer;

        if (doesExplosionRemoveNearbyWaterloggedBlocks) {
            waterloggedBlockConsumer = block -> {
                block.setType(Material.AIR);
            };
        } else if (doesExplosionRemoveWaterloggedStateFromNearbyBlocks) {
            waterloggedBlockConsumer = block -> {
                this.blockDataUtils.setIsBlockWaterlogged(block, false);
            };
        } else {
            waterloggedBlockConsumer = block -> {
            };
        }

        return waterloggedBlockConsumer;
    }

    private Consumer<Block> getLiquidBlockConsumer(EntityBehavioralConfiguration entityBehavioralConfiguration,
            boolean isSourceLocationLiquidlike) {
        final boolean doesExplosionRemoveNearbyLiquidsSurface = entityBehavioralConfiguration
                .doesExplosionRemoveNearbyLiquidsSurface() && !isSourceLocationLiquidlike;
        final boolean doesExplosionRemoveNearbyLiquidsUnderwater = entityBehavioralConfiguration
                .doesExplosionRemoveNearbyLiquidsUnderwater() && isSourceLocationLiquidlike;
        final boolean doesExplosionRemoveNearbyLiquids = entityBehavioralConfiguration
                .doesExplosionRemoveNearbyLiquids()
                && (doesExplosionRemoveNearbyLiquidsSurface || doesExplosionRemoveNearbyLiquidsUnderwater);

        final Consumer<Block> liquidBlockConsumer;

        if (doesExplosionRemoveNearbyLiquids) {
            liquidBlockConsumer = block -> {
                block.setType(Material.AIR);
            };
        } else {
            liquidBlockConsumer = block -> {
            };
        }

        return liquidBlockConsumer;
    }

    private DropCollector getDropCollector(EntityConfiguration entityConfiguration,
            Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        return entityConfiguration.doPackDroppedItems()
                ? new PackedDropCollector(materialConfigurations)
                : new UnpackedDropCollector();
    }
}
