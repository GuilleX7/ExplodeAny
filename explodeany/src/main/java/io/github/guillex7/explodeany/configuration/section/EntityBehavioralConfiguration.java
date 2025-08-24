package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

public class EntityBehavioralConfiguration {
    private static final String EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_PATH = "ExplosionRemoveWaterloggedStateFromNearbyBlocks";
    private static final String EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_SURFACE_PATH = "ExplosionRemoveWaterloggedStateFromNearbyBlocksOnSurface";
    private static final String EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_UNDERWATER_PATH = "ExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater";
    private static final String EXPLOSION_REMOVE_NEARBY_LIQUIDS_PATH = "ExplosionRemoveNearbyLiquids";
    private static final String EXPLOSION_REMOVE_NEARBY_LIQUIDS_SURFACE_PATH = "ExplosionRemoveNearbyLiquidsOnSurface";
    private static final String EXPLOSION_REMOVE_NEARBY_LIQUIDS_UNDERWATER_PATH = "ExplosionRemoveNearbyLiquidsUnderwater";
    private static final String EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_PATH = "ExplosionRemoveNearbyWaterloggedBlocks";
    private static final String EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_SURFACE_PATH = "ExplosionRemoveNearbyWaterloggedBlocksOnSurface";
    private static final String EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_UNDERWATER_PATH = "ExplosionRemoveNearbyWaterloggedBlocksUnderwater";

    private final boolean explosionRemoveWaterloggedStateFromNearbyBlocks;
    private final boolean explosionRemoveWaterloggedStateFromNearbyBlocksSurface;
    private final boolean explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater;
    private final boolean explosionRemoveNearbyLiquids;
    private final boolean explosionRemoveNearbyLiquidsSurface;
    private final boolean explosionRemoveNearbyLiquidsUnderwater;
    private final boolean explosionRemoveNearbyWaterloggedBlocks;
    private final boolean explosionRemoveNearbyWaterloggedBlocksSurface;
    private final boolean explosionRemoveNearbyWaterloggedBlocksUnderwater;

    public static EntityBehavioralConfiguration byDefault() {
        return new EntityBehavioralConfiguration(
                false,
                true,
                true,
                false,
                true,
                true,
                false,
                true,
                true);
    }

    public static EntityBehavioralConfiguration fromConfigurationSection(final ConfigurationSection section) {
        final EntityBehavioralConfiguration defaults = EntityBehavioralConfiguration.byDefault();

        return new EntityBehavioralConfiguration(
                section.getBoolean(
                        EntityBehavioralConfiguration.EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_PATH,
                        defaults.doesExplosionRemoveWaterloggedStateFromNearbyBlocks()),
                section.getBoolean(
                        EntityBehavioralConfiguration.EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_SURFACE_PATH,
                        defaults.doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface()),
                section.getBoolean(
                        EntityBehavioralConfiguration.EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater()),
                section.getBoolean(EntityBehavioralConfiguration.EXPLOSION_REMOVE_NEARBY_LIQUIDS_PATH,
                        defaults.doesExplosionRemoveNearbyLiquids()),
                section.getBoolean(EntityBehavioralConfiguration.EXPLOSION_REMOVE_NEARBY_LIQUIDS_SURFACE_PATH,
                        defaults.doesExplosionRemoveNearbyLiquidsSurface()),
                section.getBoolean(EntityBehavioralConfiguration.EXPLOSION_REMOVE_NEARBY_LIQUIDS_UNDERWATER_PATH,
                        defaults.doesExplosionRemoveNearbyLiquidsUnderwater()),
                section.getBoolean(EntityBehavioralConfiguration.EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_PATH,
                        defaults.doesExplosionRemoveNearbyWaterloggedBlocks()),
                section.getBoolean(
                        EntityBehavioralConfiguration.EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_SURFACE_PATH,
                        defaults.doesExplosionRemoveNearbyWaterloggedBlocksSurface()),
                section.getBoolean(
                        EntityBehavioralConfiguration.EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionRemoveNearbyWaterloggedBlocksUnderwater()));
    }

    public EntityBehavioralConfiguration(final boolean explosionRemoveWaterloggedStateFromNearbyBlocks,
            final boolean explosionRemoveWaterloggedStateFromNearbyBlocksSurface,
            final boolean explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater,
            final boolean explosionRemoveNearbyLiquids,
            final boolean explosionRemoveNearbyLiquidsSurface, final boolean explosionRemoveNearbyLiquidsUnderwater,
            final boolean explosionRemoveNearbyWaterloggedBlocks,
            final boolean explosionRemoveNearbyWaterloggedBlocksSurface,
            final boolean explosionRemoveNearbyWaterloggedBlocksUnderwater) {
        this.explosionRemoveWaterloggedStateFromNearbyBlocks = explosionRemoveWaterloggedStateFromNearbyBlocks;
        this.explosionRemoveWaterloggedStateFromNearbyBlocksSurface = explosionRemoveWaterloggedStateFromNearbyBlocksSurface;
        this.explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater = explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater;
        this.explosionRemoveNearbyLiquids = explosionRemoveNearbyLiquids;
        this.explosionRemoveNearbyLiquidsSurface = explosionRemoveNearbyLiquidsSurface;
        this.explosionRemoveNearbyLiquidsUnderwater = explosionRemoveNearbyLiquidsUnderwater;
        this.explosionRemoveNearbyWaterloggedBlocks = explosionRemoveNearbyWaterloggedBlocks;
        this.explosionRemoveNearbyWaterloggedBlocksSurface = explosionRemoveNearbyWaterloggedBlocksSurface;
        this.explosionRemoveNearbyWaterloggedBlocksUnderwater = explosionRemoveNearbyWaterloggedBlocksUnderwater;
    }

    public boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocks() {
        return this.explosionRemoveWaterloggedStateFromNearbyBlocks;
    }

    public boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface() {
        return this.explosionRemoveWaterloggedStateFromNearbyBlocksSurface;
    }

    public boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater() {
        return this.explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater;
    }

    public boolean doesExplosionRemoveNearbyLiquids() {
        return this.explosionRemoveNearbyLiquids;
    }

    public boolean doesExplosionRemoveNearbyLiquidsSurface() {
        return this.explosionRemoveNearbyLiquidsSurface;
    }

    public boolean doesExplosionRemoveNearbyLiquidsUnderwater() {
        return this.explosionRemoveNearbyLiquidsUnderwater;
    }

    public boolean doesExplosionRemoveNearbyWaterloggedBlocks() {
        return this.explosionRemoveNearbyWaterloggedBlocks;
    }

    public boolean doesExplosionRemoveNearbyWaterloggedBlocksSurface() {
        return this.explosionRemoveNearbyWaterloggedBlocksSurface;
    }

    public boolean doesExplosionRemoveNearbyWaterloggedBlocksUnderwater() {
        return this.explosionRemoveNearbyWaterloggedBlocksUnderwater;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(String.format("Remove nearby waterlogged blocks: %s",
                this.explosionRemoveNearbyWaterloggedBlocks ? "\n" : "false\n"));
        if (this.explosionRemoveNearbyWaterloggedBlocks) {
            builder.append(String.format(" - On surface: %s\n - Underwater: %s\n",
                    this.explosionRemoveNearbyWaterloggedBlocksSurface,
                    this.explosionRemoveNearbyWaterloggedBlocksUnderwater));
        }

        builder.append(String.format("Remove nearby waterlogged state: %s",
                this.explosionRemoveWaterloggedStateFromNearbyBlocks ? "\n" : "false\n"));
        if (this.explosionRemoveWaterloggedStateFromNearbyBlocks) {
            builder.append(String.format(" - On surface: %s\n - Underwater: %s\n",
                    this.explosionRemoveWaterloggedStateFromNearbyBlocksSurface,
                    this.explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater));
        }

        builder.append(String.format("Remove nearby liquids: %s",
                this.explosionRemoveNearbyLiquids ? "\n" : "false\n"));
        if (this.explosionRemoveNearbyLiquids) {
            builder.append(String.format(" - On surface: %s\n - Underwater: %s\n",
                    this.explosionRemoveNearbyLiquidsSurface,
                    this.explosionRemoveNearbyLiquidsUnderwater));
        }

        return builder.toString();
    }

    @Override
    public EntityBehavioralConfiguration clone() {
        return new EntityBehavioralConfiguration(
                this.explosionRemoveWaterloggedStateFromNearbyBlocks,
                this.explosionRemoveWaterloggedStateFromNearbyBlocksSurface,
                this.explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater,
                this.explosionRemoveNearbyLiquids,
                this.explosionRemoveNearbyLiquidsSurface,
                this.explosionRemoveNearbyLiquidsUnderwater,
                this.explosionRemoveNearbyWaterloggedBlocks,
                this.explosionRemoveNearbyWaterloggedBlocksSurface,
                this.explosionRemoveNearbyWaterloggedBlocksUnderwater);
    }
}
