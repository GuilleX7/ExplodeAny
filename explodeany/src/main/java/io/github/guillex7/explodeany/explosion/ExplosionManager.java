package io.github.guillex7.explodeany.explosion;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
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
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.section.EntityBehavioralConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.configuration.section.WorldHoleProtection;
import io.github.guillex7.explodeany.explosion.drop.DropCollector;
import io.github.guillex7.explodeany.explosion.drop.PackedDropCollector;
import io.github.guillex7.explodeany.explosion.drop.UnpackedDropCollector;
import io.github.guillex7.explodeany.explosion.liquid.BlockLiquidDetector;
import io.github.guillex7.explodeany.explosion.liquid.TrajectoryExplosionLiquidDetector;
import io.github.guillex7.explodeany.explosion.metadata.ExplosionMetadata;

public class ExplosionManager {
    private static ExplosionManager instance;

    public static final String EXPLOSION_MANAGER_SPAWNED_TAG = "eany-em-spawned";
    public static final String EXPLOSION_MANAGER_EXPLOSION_METADATA_TAG = "eany-em-explosion-metadata";

    private final IBlockDataUtils blockDataUtils;
    private final BlockDatabase blockDatabase;
    private final ConfigurationManager configurationManager;

    private ExplosionManager() {
        this.blockDataUtils = CompatibilityManager.getInstance().getApi().getBlockDataUtils();
        this.blockDatabase = BlockDatabase.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
    }

    public static ExplosionManager getInstance() {
        if (instance == null) {
            instance = new ExplosionManager();
        }
        return instance;
    }

    public void removeHandledBlocksFromList(final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final List<Block> blockList, final Location sourceLocation) {
        final WorldHoleProtection worldHoleProtection = this.configurationManager
                .getWorldHoleProtection(sourceLocation.getWorld().getName());

        final Iterator<Block> iterator = blockList.iterator();
        if (!worldHoleProtection.doProtectUnhandledBlocks()) {
            while (iterator.hasNext()) {
                Block block = iterator.next();
                if (materialConfigurations.containsKey(block.getType())) {
                    iterator.remove();
                }
            }
        } else {
            while (iterator.hasNext()) {
                Block block = iterator.next();
                if (materialConfigurations.containsKey(block.getType())
                        || worldHoleProtection.isHeightProtected(block.getY())) {
                    iterator.remove();
                }
            }
        }
    }

    private void attachExplosionManagerMetadataToEntity(final Entity entity,
            final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final DropCollector dropCollector) {
        entity.setMetadata(EXPLOSION_MANAGER_SPAWNED_TAG,
                new FixedMetadataValue(ExplodeAny.getInstance(), true));
        entity.setMetadata(EXPLOSION_MANAGER_EXPLOSION_METADATA_TAG,
                new FixedMetadataValue(ExplodeAny.getInstance(),
                        new ExplosionMetadata(materialConfigurations, dropCollector)));
    }

    public final boolean isEntitySpawnedByExplosionManager(Entity entity) {
        return entity.hasMetadata(EXPLOSION_MANAGER_SPAWNED_TAG);
    }

    public final ExplosionMetadata getExplosionManagerMetadataFromEntity(Entity entity) {
        List<MetadataValue> metadataValueList = entity.getMetadata(EXPLOSION_MANAGER_EXPLOSION_METADATA_TAG);

        if (!metadataValueList.isEmpty()) {
            return (ExplosionMetadata) metadataValueList.get(0).value();
        } else {
            // This should never happen, but just in case.
            return new ExplosionMetadata(new HashMap<>(), new UnpackedDropCollector());
        }
    }

    public final boolean manageExplosion(final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final EntityConfiguration entityConfiguration, final Location sourceLocation,
            final double originalRawExplosionRadius) {
        return this.manageExplosion(materialConfigurations, entityConfiguration, sourceLocation,
                originalRawExplosionRadius, EnumSet.noneOf(ExplosionFlag.class));
    }

    public final boolean manageExplosion(final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final EntityConfiguration entityConfiguration, final Location sourceLocation,
            final double originalRawExplosionRadius,
            final EnumSet<ExplosionFlag> flags) {
        double rawExplosionRadius = entityConfiguration.getExplosionRadius() != 0d
                ? entityConfiguration.getExplosionRadius()
                : originalRawExplosionRadius;

        final boolean isSourceLocationUnderwater = BlockLiquidDetector.isLocationLiquidlike(sourceLocation)
                || flags.contains(ExplosionFlag.FORCE_IS_SOURCE_LOCATION_UNDERWATER);

        if (isSourceLocationUnderwater) {
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
        final String sourceWorldName = sourceWorld.getName();
        final Location sourceBlockLocation = new Location(sourceWorld, cx, cy, cz);

        final EntityBehavioralConfiguration entityBehavioralConfiguration = entityConfiguration
                .getEntityBehavioralConfiguration();
        final WorldHoleProtection worldHoleProtection = this.configurationManager
                .getWorldHoleProtection(sourceWorldName);

        final BiConsumer<Block, Boolean> waterloggedBlockConsumer = this.getWaterloggedBlockConsumer(
                entityBehavioralConfiguration,
                isSourceLocationUnderwater);
        final Consumer<Block> liquidBlockConsumer = this.getLiquidBlockConsumer(entityBehavioralConfiguration,
                isSourceLocationUnderwater);
        final DropCollector dropCollector = this.getDropCollector(entityConfiguration, materialConfigurations);

        // Hint: This is a very expensive operation, so we only do it if we have to.
        if (!materialConfigurations.isEmpty() || entityBehavioralConfiguration.doesExplosionRemoveNearbyLiquids()
                || entityBehavioralConfiguration.doesExplosionRemoveWaterloggedStateFromNearbyBlocks()
                || entityBehavioralConfiguration.doesExplosionRemoveNearbyWaterloggedBlocks()) {
            final long currentTime = System.currentTimeMillis();

            for (int y = cy - explosionRadius; y <= cypr; y++) {
                if (worldHoleProtection.isHeightProtected(y)) {
                    continue;
                }

                for (int x = cx - explosionRadius; x <= cxpr; x++) {
                    for (int z = cz - explosionRadius; z <= czpr; z++) {
                        final int dx = x - cx;
                        final int dy = y - cy;
                        final int dz = z - cz;
                        final int squaredDistance = dx * dx + dy * dy + dz * dz;

                        if (squaredExplosionRadius < squaredDistance) {
                            continue;
                        }

                        final Block block = sourceWorld.getBlockAt(x, y, z);
                        if (block.isEmpty()) {
                            continue;
                        }

                        final EntityMaterialConfiguration materialConfiguration = materialConfigurations
                                .get(block.getType());
                        final boolean isBlockHandled = materialConfiguration != null;

                        if (this.blockDataUtils.isBlockWaterlogged(block)) {
                            waterloggedBlockConsumer.accept(block, isBlockHandled);
                        } else if (block.isLiquid()) {
                            liquidBlockConsumer.accept(block);
                        }

                        if (!isBlockHandled) {
                            continue;
                        }

                        this.damageBlock(materialConfiguration, block, sourceBlockLocation,
                                explosionRadius, squaredExplosionRadius,
                                squaredDistance, isSourceLocationUnderwater, dropCollector, currentTime);
                    }
                }
            }
        }

        entityConfiguration.getOnExplodeSoundConfiguration().playAt(sourceLocation);
        entityConfiguration.getOnExplodeParticleConfiguration().spawnAt(sourceLocation);

        if (entityConfiguration.doesExplosionDamageBlocksUnderwater() && isSourceLocationUnderwater) {
            if (!flags.contains(ExplosionFlag.FORCE_DISABLE_VANILLA_UNDERWATER_DAMAGE)) {
                if (sourceLocation.getBlock().isLiquid()) {
                    sourceLocation.getBlock().setType(Material.AIR);
                } else {
                    this.blockDataUtils.setIsBlockWaterlogged(sourceLocation.getBlock(), false);
                }
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

    private void spawnManagedExplosion(final Location location,
            final Map<Material, EntityMaterialConfiguration> materialConfigurations, final double explosionRadius,
            final DropCollector dropCollector) {
        final TNTPrimed explosiveEntity = location.getWorld().spawn(location, TNTPrimed.class);
        this.attachExplosionManagerMetadataToEntity(explosiveEntity, materialConfigurations, dropCollector);
        explosiveEntity.setFuseTicks(0);
        explosiveEntity.setYield((float) explosionRadius);
    }

    private void damageBlock(final EntityMaterialConfiguration materialConfiguration, final Block targetBlock,
            final Location sourceBlockLocation, final int explosionRadius, final double squaredExplosionRadius,
            final double squaredDistance,
            final boolean isSourceLocationUnderwater, final DropCollector dropCollector, final long currentTime) {
        final Location targetBlockLocation = targetBlock.getLocation();

        double effectiveDamage = materialConfiguration.getDamage();

        if (materialConfiguration.isUnderwaterAffected()
                && this.areUnderwaterRulesApplicableForTargetBlock(materialConfiguration, sourceBlockLocation,
                        targetBlockLocation,
                        isSourceLocationUnderwater, explosionRadius)) {
            effectiveDamage *= materialConfiguration.getUnderwaterDamageFactor();
        }

        effectiveDamage *= 1
                - materialConfiguration.getDistanceAttenuationFactor() * (squaredDistance - 1) / squaredExplosionRadius;

        final BlockStatus affectedBlockStatus = this.blockDatabase.getOrCreateBlockStatus(targetBlock);
        affectedBlockStatus.damage(effectiveDamage, currentTime);

        if (affectedBlockStatus.shouldBreak()) {
            materialConfiguration.getOnBreakSoundConfiguration().playAt(targetBlockLocation);
            materialConfiguration.getOnBreakParticleConfiguration().spawnAt(targetBlockLocation);

            final Material targetBlockMaterial = targetBlock.getType();
            targetBlock.setType(Material.AIR);
            this.blockDatabase.removeBlockStatus(targetBlock);
            if (materialConfiguration.shouldBeDropped()) {
                dropCollector.collect(materialConfiguration.getDropMaterial() == null ? targetBlockMaterial
                        : materialConfiguration.getDropMaterial(), targetBlockLocation);
            }
        } else {
            materialConfiguration.getOnHitSoundConfiguration().playAt(targetBlockLocation);
            materialConfiguration.getOnHitParticleConfiguration().spawnAt(targetBlockLocation);
        }
    }

    private boolean areUnderwaterRulesApplicableForTargetBlock(final EntityMaterialConfiguration materialConfiguration,
            final Location sourceBlockLocation,
            final Location targetBlockLocation,
            final boolean isSourceLocationUnderwater, final int explosionRadius) {
        return materialConfiguration.isFancyUnderwaterDetection()
                ? TrajectoryExplosionLiquidDetector.isLiquidInTrajectory(sourceBlockLocation, targetBlockLocation,
                        explosionRadius)
                : isSourceLocationUnderwater;
    }

    private BiConsumer<Block, Boolean> getWaterloggedBlockConsumer(
            EntityBehavioralConfiguration entityBehavioralConfiguration,
            boolean isSourceLocationUnderwater) {
        if (entityBehavioralConfiguration
                .doesExplosionRemoveNearbyWaterloggedBlocks()) {
            final boolean doSurfaceRulesApply = entityBehavioralConfiguration
                    .doesExplosionRemoveNearbyWaterloggedBlocksSurface() && !isSourceLocationUnderwater;
            final boolean doUnderwaterRulesApply = entityBehavioralConfiguration
                    .doesExplosionRemoveNearbyWaterloggedBlocksUnderwater() && isSourceLocationUnderwater;

            if (doSurfaceRulesApply || doUnderwaterRulesApply) {
                return (block, isHandled) -> {
                    if (!isHandled) {
                        block.setType(Material.AIR);
                    } else {
                        this.blockDataUtils.setIsBlockWaterlogged(block, false);
                    }
                };
            }
        } else if (entityBehavioralConfiguration.doesExplosionRemoveWaterloggedStateFromNearbyBlocks()) {
            final boolean doSurfaceRulesApply = entityBehavioralConfiguration
                    .doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface() && !isSourceLocationUnderwater;
            final boolean doUnderwaterRulesApply = entityBehavioralConfiguration
                    .doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater() && isSourceLocationUnderwater;

            if (doSurfaceRulesApply || doUnderwaterRulesApply) {
                return (block, isHandled) -> {
                    this.blockDataUtils.setIsBlockWaterlogged(block, false);
                };
            }
        }

        return (block, isHandled) -> {
            // No action needed
        };
    }

    private Consumer<Block> getLiquidBlockConsumer(final EntityBehavioralConfiguration entityBehavioralConfiguration,
            final boolean isSourceLocationUnderwater) {
        if (entityBehavioralConfiguration.doesExplosionRemoveNearbyLiquids()) {
            final boolean doSurfaceRulesApply = entityBehavioralConfiguration
                    .doesExplosionRemoveNearbyLiquidsSurface() && !isSourceLocationUnderwater;
            final boolean doUnderwaterRulesApply = entityBehavioralConfiguration
                    .doesExplosionRemoveNearbyLiquidsUnderwater() && isSourceLocationUnderwater;

            if (doSurfaceRulesApply || doUnderwaterRulesApply) {
                return block -> {
                    block.setType(Material.AIR);
                };
            }
        }

        return block -> {
            // No action needed
        };
    }

    private DropCollector getDropCollector(final EntityConfiguration entityConfiguration,
            final Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        return entityConfiguration.doPackDroppedItems()
                ? new PackedDropCollector(materialConfigurations)
                : new UnpackedDropCollector();
    }
}
