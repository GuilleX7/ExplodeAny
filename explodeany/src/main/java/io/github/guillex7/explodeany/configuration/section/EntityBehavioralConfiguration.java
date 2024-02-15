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

    private boolean explosionRemoveWaterloggedStateFromNearbyBlocks;
    private boolean explosionRemoveWaterloggedStateFromNearbyBlocksSurface;
    private boolean explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater;
    private boolean explosionRemoveNearbyLiquids;
    private boolean explosionRemoveNearbyLiquidsSurface;
    private boolean explosionRemoveNearbyLiquidsUnderwater;
    private boolean explosionRemoveNearbyWaterloggedBlocks;
    private boolean explosionRemoveNearbyWaterloggedBlocksSurface;
    private boolean explosionRemoveNearbyWaterloggedBlocksUnderwater;

    public static EntityBehavioralConfiguration of(boolean explosionRemoveWaterloggedStateFromNearbyBlocks,
            boolean explosionRemoveWaterloggedStateFromNearbyBlocksSurface,
            boolean explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater, boolean explosionRemoveNearbyLiquids,
            boolean explosionRemoveNearbyLiquidsSurface, boolean explosionRemoveNearbyLiquidsUnderwater,
            boolean explosionRemoveNearbyWaterloggedBlocks, boolean explosionRemoveNearbyWaterloggedBlocksSurface,
            boolean explosionRemoveNearbyWaterloggedBlocksUnderwater) {
        return new EntityBehavioralConfiguration(explosionRemoveWaterloggedStateFromNearbyBlocks,
                explosionRemoveWaterloggedStateFromNearbyBlocksSurface,
                explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater, explosionRemoveNearbyLiquids,
                explosionRemoveNearbyLiquidsSurface, explosionRemoveNearbyLiquidsUnderwater,
                explosionRemoveNearbyWaterloggedBlocks, explosionRemoveNearbyWaterloggedBlocksSurface,
                explosionRemoveNearbyWaterloggedBlocksUnderwater);
    }

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

    public static EntityBehavioralConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityBehavioralConfiguration defaults = EntityBehavioralConfiguration.byDefault();

        return new EntityBehavioralConfiguration(
                section.getBoolean(EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_PATH,
                        defaults.doesExplosionRemoveWaterloggedStateFromNearbyBlocks()),
                section.getBoolean(EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_SURFACE_PATH,
                        defaults.doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface()),
                section.getBoolean(EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater()),
                section.getBoolean(EXPLOSION_REMOVE_NEARBY_LIQUIDS_PATH, defaults.doesExplosionRemoveNearbyLiquids()),
                section.getBoolean(EXPLOSION_REMOVE_NEARBY_LIQUIDS_SURFACE_PATH,
                        defaults.doesExplosionRemoveNearbyLiquidsSurface()),
                section.getBoolean(EXPLOSION_REMOVE_NEARBY_LIQUIDS_UNDERWATER_PATH,
                        defaults.doesExplosionRemoveNearbyLiquidsUnderwater()),
                section.getBoolean(EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_PATH,
                        defaults.doesExplosionRemoveNearbyWaterloggedBlocks()),
                section.getBoolean(EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_SURFACE_PATH,
                        defaults.doesExplosionRemoveNearbyWaterloggedBlocksSurface()),
                section.getBoolean(EXPLOSION_REMOVE_NEARBY_WATERLOGGED_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionRemoveNearbyWaterloggedBlocksUnderwater()));
    }

    public EntityBehavioralConfiguration(boolean explosionRemoveWaterloggedStateFromNearbyBlocks,
            boolean explosionRemoveWaterloggedStateFromNearbyBlocksSurface,
            boolean explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater, boolean explosionRemoveNearbyLiquids,
            boolean explosionRemoveNearbyLiquidsSurface, boolean explosionRemoveNearbyLiquidsUnderwater,
            boolean explosionRemoveNearbyWaterloggedBlocks, boolean explosionRemoveNearbyWaterloggedBlocksSurface,
            boolean explosionRemoveNearbyWaterloggedBlocksUnderwater) {
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
        return explosionRemoveWaterloggedStateFromNearbyBlocks;
    }

    public boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocksSurface() {
        return explosionRemoveWaterloggedStateFromNearbyBlocksSurface;
    }

    public boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocksUnderwater() {
        return explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater;
    }

    public boolean doesExplosionRemoveNearbyLiquids() {
        return explosionRemoveNearbyLiquids;
    }

    public boolean doesExplosionRemoveNearbyLiquidsSurface() {
        return explosionRemoveNearbyLiquidsSurface;
    }

    public boolean doesExplosionRemoveNearbyLiquidsUnderwater() {
        return explosionRemoveNearbyLiquidsUnderwater;
    }

    public boolean doesExplosionRemoveNearbyWaterloggedBlocks() {
        return explosionRemoveNearbyWaterloggedBlocks;
    }

    public boolean doesExplosionRemoveNearbyWaterloggedBlocksSurface() {
        return explosionRemoveNearbyWaterloggedBlocksSurface;
    }

    public boolean doesExplosionRemoveNearbyWaterloggedBlocksUnderwater() {
        return explosionRemoveNearbyWaterloggedBlocksUnderwater;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("Remove nearby waterlogged state: %s",
                explosionRemoveWaterloggedStateFromNearbyBlocks ? "\n" : "false\n"));
        if (explosionRemoveWaterloggedStateFromNearbyBlocks) {
            builder.append(String.format(" - On surface: %s\n - Underwater: %s\n",
                    explosionRemoveWaterloggedStateFromNearbyBlocksSurface,
                    explosionRemoveWaterloggedStateFromNearbyBlocksUnderwater));
        }

        builder.append(String.format("Remove nearby liquids: %s",
                explosionRemoveNearbyLiquids ? "\n" : "false\n"));
        if (explosionRemoveNearbyLiquids) {
            builder.append(String.format(" - On surface: %s\n - Underwater: %s\n",
                    explosionRemoveNearbyLiquidsSurface,
                    explosionRemoveNearbyLiquidsUnderwater));
        }

        builder.append(String.format("Remove nearby waterlogged blocks: %s",
                explosionRemoveNearbyWaterloggedBlocks ? "\n" : "false\n"));
        if (explosionRemoveNearbyWaterloggedBlocks) {
            builder.append(String.format(" - On surface: %s\n - Underwater: %s\n",
                    explosionRemoveNearbyWaterloggedBlocksSurface,
                    explosionRemoveNearbyWaterloggedBlocksUnderwater));
        }

        return builder.toString();
    }
}
