package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.util.MathUtils;

public class EntityConfiguration {
    private static final String EXPLOSION_RADIUS_PATH = "ExplosionRadius";
    private static final String EXPLOSION_FACTOR_PATH = "ExplosionFactor";
    private static final String REPLACE_ORIGINAL_EXPLOSION_PATH = "ReplaceOriginalExplosion";
    private static final String UNDERWATER_EXPLOSION_FACTOR_PATH = "UnderwaterExplosionFactor";
    private static final String EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH = "ExplosionDamageBlocksUnderwater";
    private static final String REPLACE_ORIGINAL_EXPLOSION_WHEN_UNDERWATER = "ReplaceOriginalExplosionWhenUnderwater";
    private static final String EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS = "ExplosionRemoveWaterloggedStateFromNearbyBlocks";
    private static final String EXPLOSION_REMOVE_NEARBY_LIQUIDS = "ExplosionRemoveNearbyLiquids";

    private double explosionRadius;
    private double explosionFactor;
    private boolean replaceOriginalExplosion;
    private double underwaterExplosionFactor;
    private boolean explosionDamageBlocksUnderwater;
    private boolean replaceOriginalExplosionWhenUnderwater;
    private boolean explosionRemoveWaterloggedStateFromNearbyBlocks;
    private boolean explosionRemoveNearbyLiquid;
    private SoundConfiguration soundConfiguration;
    private ParticleConfiguration particleConfiguration;

    public static EntityConfiguration of(double explosionRadius, double explosionFactor,
            boolean replaceOriginalExplosion,
            double underwaterExplosionFactor, boolean explosionDamageBlocksUnderwater,
            boolean replaceOriginalExplosionWhenUnderwater, boolean explosionRemoveWaterloggedStateFromNearbyBlocks,
            boolean explosionRemoveNearbyLiquid, SoundConfiguration soundConfiguration,
            ParticleConfiguration particleConfiguration) {
        return new EntityConfiguration(explosionRadius, explosionFactor, replaceOriginalExplosion,
                underwaterExplosionFactor, explosionDamageBlocksUnderwater, replaceOriginalExplosionWhenUnderwater,
                explosionRemoveWaterloggedStateFromNearbyBlocks, explosionRemoveNearbyLiquid, soundConfiguration,
                particleConfiguration);
    }

    public static EntityConfiguration byDefault() {
        return EntityConfiguration.of(0.0d, 1.0d, false, 0.5d, false, false, false, false,
                SoundConfiguration.byDefault(),
                ParticleConfiguration.byDefault());
    }

    public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityConfiguration defaults = EntityConfiguration.byDefault();
        ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SoundConfiguration.ROOT_PATH);
        ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(ParticleConfiguration.ROOT_PATH);

        return EntityConfiguration.of(
                MathUtils.ensureMin(section.getDouble(EXPLOSION_RADIUS_PATH, defaults.getExplosionRadius()), 0.0d),
                MathUtils.ensureMin(section.getDouble(EXPLOSION_FACTOR_PATH, defaults.getExplosionFactor()), 0.0d),
                section.getBoolean(REPLACE_ORIGINAL_EXPLOSION_PATH, defaults.doReplaceOriginalExplosion()),
                MathUtils.ensureMin(
                        section.getDouble(UNDERWATER_EXPLOSION_FACTOR_PATH, defaults.getUnderwaterExplosionFactor()),
                        0.0d),
                section.getBoolean(EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionDamageBlocksUnderwater()),
                section.getBoolean(REPLACE_ORIGINAL_EXPLOSION_WHEN_UNDERWATER,
                        defaults.doReplaceOriginalExplosionWhenUnderwater()),
                section.getBoolean(EXPLOSION_REMOVE_WATERLOGGED_STATE_FROM_NEARBY_BLOCKS,
                        defaults.doesExplosionRemoveWaterloggedStateFromNearbyBlocks()),
                section.getBoolean(EXPLOSION_REMOVE_NEARBY_LIQUIDS,
                        defaults.doesExplosionRemoveNearbyLiquid()),
                (soundConfigurationSection != null)
                        ? SoundConfiguration.fromConfigurationSection(soundConfigurationSection)
                        : SoundConfiguration.byDefault(),
                (particleConfigurationSection != null)
                        ? ParticleConfiguration.fromConfigurationSection(particleConfigurationSection)
                        : ParticleConfiguration.byDefault());
    }

    public EntityConfiguration(double explosionRadius, double explosionFactor, boolean replaceOriginalExplosion,
            double underwaterExplosionFactor, boolean explosionDamageBlocksUnderwater,
            boolean replaceOriginalExplosionWhenUnderwater, boolean explosionRemoveWaterloggedStateFromNearbyBlocks,
            boolean explosionRemoveNearbyLiquid, SoundConfiguration soundConfiguration,
            ParticleConfiguration particleConfiguration) {
        this.explosionRadius = explosionRadius;
        this.explosionFactor = explosionFactor;
        this.replaceOriginalExplosion = replaceOriginalExplosion;
        this.underwaterExplosionFactor = underwaterExplosionFactor;
        this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
        this.replaceOriginalExplosionWhenUnderwater = replaceOriginalExplosionWhenUnderwater;
        this.explosionRemoveWaterloggedStateFromNearbyBlocks = explosionRemoveWaterloggedStateFromNearbyBlocks;
        this.explosionRemoveNearbyLiquid = explosionRemoveNearbyLiquid;
        this.soundConfiguration = soundConfiguration;
        this.particleConfiguration = particleConfiguration;
    }

    public double getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(double explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public double getExplosionFactor() {
        return explosionFactor;
    }

    public boolean doReplaceOriginalExplosion() {
        return replaceOriginalExplosion;
    }

    public void setReplaceOriginalExplosion(boolean replaceOriginalExplosion) {
        this.replaceOriginalExplosion = replaceOriginalExplosion;
    }

    public void setExplosionFactor(double explosionFactor) {
        this.explosionFactor = explosionFactor;
    }

    public double getUnderwaterExplosionFactor() {
        return underwaterExplosionFactor;
    }

    public void setUnderwaterExplosionFactor(double underwaterExplosionFactor) {
        this.underwaterExplosionFactor = underwaterExplosionFactor;
    }

    public boolean doesExplosionDamageBlocksUnderwater() {
        return explosionDamageBlocksUnderwater;
    }

    public void setExplosionDamageBlocksUnderwater(boolean explosionDamageBlocksUnderwater) {
        this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
    }

    public boolean doReplaceOriginalExplosionWhenUnderwater() {
        return replaceOriginalExplosionWhenUnderwater;
    }

    public void setReplaceOriginalExplosionWhenUnderwater(boolean replaceOriginalExplosionWhenUnderwater) {
        this.replaceOriginalExplosionWhenUnderwater = replaceOriginalExplosionWhenUnderwater;
    }

    public boolean doesExplosionRemoveWaterloggedStateFromNearbyBlocks() {
        return explosionRemoveWaterloggedStateFromNearbyBlocks;
    }

    public void setExplosionRemoveWaterloggedStateFromNearbyBlocks(
            boolean explosionRemoveWaterloggedStateFromNearbyBlocks) {
        this.explosionRemoveWaterloggedStateFromNearbyBlocks = explosionRemoveWaterloggedStateFromNearbyBlocks;
    }

    public boolean doesExplosionRemoveNearbyLiquid() {
        return explosionRemoveNearbyLiquid;
    }

    public void setExplosionRemoveNearbyLiquid(boolean explosionRemoveNearbyLiquid) {
        this.explosionRemoveNearbyLiquid = explosionRemoveNearbyLiquid;
    }

    public SoundConfiguration getSoundConfiguration() {
        return soundConfiguration;
    }

    public void setSoundConfiguration(SoundConfiguration soundConfiguration) {
        this.soundConfiguration = soundConfiguration;
    }

    public ParticleConfiguration getParticleConfiguration() {
        return particleConfiguration;
    }

    public void setParticleConfiguration(ParticleConfiguration particleConfiguration) {
        this.particleConfiguration = particleConfiguration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(explosionRadius);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(explosionFactor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (replaceOriginalExplosion ? 1231 : 1237);
        temp = Double.doubleToLongBits(underwaterExplosionFactor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (explosionDamageBlocksUnderwater ? 1231 : 1237);
        result = prime * result + (replaceOriginalExplosionWhenUnderwater ? 1231 : 1237);
        result = prime * result + (explosionRemoveWaterloggedStateFromNearbyBlocks ? 1231 : 1237);
        result = prime * result + (explosionRemoveNearbyLiquid ? 1231 : 1237);
        result = prime * result + ((soundConfiguration == null) ? 0 : soundConfiguration.hashCode());
        result = prime * result + ((particleConfiguration == null) ? 0 : particleConfiguration.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityConfiguration other = (EntityConfiguration) obj;
        if (Double.doubleToLongBits(explosionRadius) != Double.doubleToLongBits(other.explosionRadius))
            return false;
        if (Double.doubleToLongBits(explosionFactor) != Double.doubleToLongBits(other.explosionFactor))
            return false;
        if (replaceOriginalExplosion != other.replaceOriginalExplosion)
            return false;
        if (Double.doubleToLongBits(underwaterExplosionFactor) != Double
                .doubleToLongBits(other.underwaterExplosionFactor))
            return false;
        if (explosionDamageBlocksUnderwater != other.explosionDamageBlocksUnderwater)
            return false;
        if (replaceOriginalExplosionWhenUnderwater != other.replaceOriginalExplosionWhenUnderwater)
            return false;
        if (explosionRemoveWaterloggedStateFromNearbyBlocks != other.explosionRemoveWaterloggedStateFromNearbyBlocks)
            return false;
        if (explosionRemoveNearbyLiquid != other.explosionRemoveNearbyLiquid)
            return false;
        if (soundConfiguration == null) {
            if (other.soundConfiguration != null)
                return false;
        } else if (!soundConfiguration.equals(other.soundConfiguration))
            return false;
        if (particleConfiguration == null) {
            if (other.particleConfiguration != null)
                return false;
        } else if (!particleConfiguration.equals(other.particleConfiguration))
            return false;
        return true;
    }
}
