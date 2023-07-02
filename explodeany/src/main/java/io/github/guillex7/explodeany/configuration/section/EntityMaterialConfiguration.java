package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class EntityMaterialConfiguration {
    private static final String DAMAGE_PATH = "Damage";
    private static final String DROP_CHANCE_PATH = "DropChance";
    private static final String DISTANCE_ATTENUATION_FACTOR_PATH = "DistanceAttenuationFactor";
    private static final String EXPLOSION_RADIUS_FACTOR_PATH = "ExplosionRadiusFactor";
    private static final String UNDERWATER_DAMAGE_FACTOR_PATH = "UnderwaterDamageFactor";
    private static final String FANCY_UNDERWATER_DETECTION_PATH = "FancyUnderwaterDetection";

    private double damage;
    private double dropChance;
    private double distanceAttenuationFactor;
    private double explosionRadiusFactor;
    private double underwaterDamageFactor;
    private boolean fancyUnderwaterDetection;
    private SoundConfiguration soundConfiguration;
    private ParticleConfiguration particleConfiguration;

    public static EntityMaterialConfiguration of(double damage, double dropChance, double distanceAttenuationFactor,
            double explosionRadiusFactor, double underwaterDamageFactor, boolean fancyUnderwaterDetection,
            SoundConfiguration soundConfiguration, ParticleConfiguration particleConfiguration) {
        return new EntityMaterialConfiguration(damage, dropChance, distanceAttenuationFactor, explosionRadiusFactor,
                underwaterDamageFactor, fancyUnderwaterDetection, soundConfiguration, particleConfiguration);
    }

    public static EntityMaterialConfiguration byDefault() {
        return EntityMaterialConfiguration.of(ConfigurationManager.getInstance().getBlockDurability(), 0.0d, 0.0d, 0.5d,
                0.5d, false, SoundConfiguration.byDefault(), ParticleConfiguration.byDefault());
    }

    public static EntityMaterialConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityMaterialConfiguration defaults = EntityMaterialConfiguration.byDefault();
        ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SoundConfiguration.ROOT_PATH);
        ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(ParticleConfiguration.ROOT_PATH);

        return EntityMaterialConfiguration.of(
                MathUtils.ensureMin(section.getDouble(DAMAGE_PATH, defaults.getDamage()), 0.0d),
                MathUtils.ensureRange(section.getDouble(DROP_CHANCE_PATH, defaults.getDropChance()), 100.0d,
                        0.0d) / 100.0,
                MathUtils.ensureRange(
                        section.getDouble(DISTANCE_ATTENUATION_FACTOR_PATH, defaults.getDistanceAttenuationFactor()),
                        1.0d, 0.0d),
                MathUtils.ensureMin(
                        section.getDouble(EXPLOSION_RADIUS_FACTOR_PATH, defaults.getExplosionRadiusFactor()), 0.0d),
                MathUtils.ensureMin(
                        section.getDouble(UNDERWATER_DAMAGE_FACTOR_PATH, defaults.getUnderwaterDamageFactor()), 0.0d),
                section.getBoolean(FANCY_UNDERWATER_DETECTION_PATH, defaults.isFancyUnderwaterDetection()),
                (soundConfigurationSection != null)
                        ? SoundConfiguration.fromConfigurationSection(soundConfigurationSection)
                        : SoundConfiguration.byDefault(),
                (particleConfigurationSection != null)
                        ? ParticleConfiguration.fromConfigurationSection(particleConfigurationSection)
                        : ParticleConfiguration.byDefault());
    }

    private EntityMaterialConfiguration(double damage, double dropChance, double distanceAttenuationFactor,
            double explosionRadiusFactor, double underwaterDamageFactor, boolean fancyUnderwaterDetection,
            SoundConfiguration soundConfiguration, ParticleConfiguration particleConfiguration) {
        super();
        this.damage = damage;
        this.dropChance = dropChance;
        this.distanceAttenuationFactor = distanceAttenuationFactor;
        this.explosionRadiusFactor = explosionRadiusFactor;
        this.underwaterDamageFactor = underwaterDamageFactor;
        this.fancyUnderwaterDetection = fancyUnderwaterDetection;
        this.soundConfiguration = soundConfiguration;
        this.particleConfiguration = particleConfiguration;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDropChance() {
        return dropChance;
    }

    public boolean shouldBeDropped() {
        return Math.random() <= getDropChance();
    }

    public void setDropChance(double dropChance) {
        this.dropChance = dropChance;
    }

    public double getDistanceAttenuationFactor() {
        return distanceAttenuationFactor;
    }

    public void setDistanceAttenuationFactor(double distanceAttenuationFactor) {
        this.distanceAttenuationFactor = distanceAttenuationFactor;
    }

    public double getExplosionRadiusFactor() {
        return explosionRadiusFactor;
    }

    public void setExplosionRadiusFactor(double explosionRadiusFactor) {
        this.explosionRadiusFactor = explosionRadiusFactor;
    }

    public double getUnderwaterDamageFactor() {
        return underwaterDamageFactor;
    }

    public boolean isUnderwaterAffected() {
        return getUnderwaterDamageFactor() != 1.0;
    }

    public void setUnderwaterDamageFactor(double underwaterDamageFactor) {
        this.underwaterDamageFactor = underwaterDamageFactor;
    }

    public boolean isFancyUnderwaterDetection() {
        return fancyUnderwaterDetection;
    }

    public void setFancyUnderwaterDetection(boolean fancyUnderwaterDetection) {
        this.fancyUnderwaterDetection = fancyUnderwaterDetection;
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
        temp = Double.doubleToLongBits(damage);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(distanceAttenuationFactor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dropChance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(explosionRadiusFactor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (fancyUnderwaterDetection ? 1231 : 1237);
        result = prime * result + ((particleConfiguration == null) ? 0 : particleConfiguration.hashCode());
        result = prime * result + ((soundConfiguration == null) ? 0 : soundConfiguration.hashCode());
        temp = Double.doubleToLongBits(underwaterDamageFactor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        EntityMaterialConfiguration other = (EntityMaterialConfiguration) obj;
        if (Double.doubleToLongBits(damage) != Double.doubleToLongBits(other.damage))
            return false;
        if (Double.doubleToLongBits(distanceAttenuationFactor) != Double
                .doubleToLongBits(other.distanceAttenuationFactor))
            return false;
        if (Double.doubleToLongBits(dropChance) != Double.doubleToLongBits(other.dropChance))
            return false;
        if (Double.doubleToLongBits(explosionRadiusFactor) != Double.doubleToLongBits(other.explosionRadiusFactor))
            return false;
        if (fancyUnderwaterDetection != other.fancyUnderwaterDetection)
            return false;
        if (particleConfiguration == null) {
            if (other.particleConfiguration != null)
                return false;
        } else if (!particleConfiguration.equals(other.particleConfiguration))
            return false;
        if (soundConfiguration == null) {
            if (other.soundConfiguration != null)
                return false;
        } else if (!soundConfiguration.equals(other.soundConfiguration))
            return false;
        if (Double.doubleToLongBits(underwaterDamageFactor) != Double.doubleToLongBits(other.underwaterDamageFactor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EntityMaterialConfiguration [damage=" + damage + ", dropChance=" + dropChance
                + ", distanceAttenuationFactor=" + distanceAttenuationFactor + ", explosionRadiusFactor="
                + explosionRadiusFactor + ", underwaterDamageFactor=" + underwaterDamageFactor
                + ", fancyUnderwaterDetection=" + fancyUnderwaterDetection + ", soundConfiguration="
                + soundConfiguration + ", particleConfiguration=" + particleConfiguration + "]";
    }
}
