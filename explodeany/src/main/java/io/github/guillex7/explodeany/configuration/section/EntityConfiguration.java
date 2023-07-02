package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.util.MathUtils;

public class EntityConfiguration {
    private static final String EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH = "ExplosionDamageBlocksUnderwater";
    private static final String UNDERWATER_EXPLOSION_FACTOR_PATH = "UnderwaterExplosionFactor";

    private boolean explosionDamageBlocksUnderwater;
    private Double underwaterExplosionFactor;
    private SoundConfiguration soundConfiguration;
    private ParticleConfiguration particleConfiguration;

    public static EntityConfiguration of(boolean explosionDamageBlocksUnderwater, Double underwaterExplosionFactor,
            SoundConfiguration soundConfiguration, ParticleConfiguration particleConfiguration) {
        return new EntityConfiguration(explosionDamageBlocksUnderwater, underwaterExplosionFactor, soundConfiguration,
                particleConfiguration);
    }

    public static EntityConfiguration byDefault() {
        return EntityConfiguration.of(false, 0.5d, SoundConfiguration.byDefault(), ParticleConfiguration.byDefault());
    }

    public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityConfiguration defaults = EntityConfiguration.byDefault();
        ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SoundConfiguration.ROOT_PATH);
        ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(ParticleConfiguration.ROOT_PATH);

        return EntityConfiguration.of(
                section.getBoolean(EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionDamageBlocksUnderwater()),
                MathUtils.ensureMin(
                        section.getDouble(UNDERWATER_EXPLOSION_FACTOR_PATH, defaults.getUnderwaterExplosionFactor()),
                        0.0d),
                (soundConfigurationSection != null)
                        ? SoundConfiguration.fromConfigurationSection(soundConfigurationSection)
                        : SoundConfiguration.byDefault(),
                (particleConfigurationSection != null)
                        ? ParticleConfiguration.fromConfigurationSection(particleConfigurationSection)
                        : ParticleConfiguration.byDefault());
    }

    private EntityConfiguration(boolean explosionDamageBlocksUnderwater, Double underwaterExplosionFactor,
            SoundConfiguration soundConfiguration, ParticleConfiguration particleConfiguration) {
        super();
        this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
        this.underwaterExplosionFactor = underwaterExplosionFactor;
        this.soundConfiguration = soundConfiguration;
        this.particleConfiguration = particleConfiguration;
    }

    public boolean doesExplosionDamageBlocksUnderwater() {
        return explosionDamageBlocksUnderwater;
    }

    public void setExplosionDamageBlocksUnderwater(boolean explosionDamageBlocksUnderwater) {
        this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
    }

    public Double getUnderwaterExplosionFactor() {
        return underwaterExplosionFactor;
    }

    public void setUnderwaterExplosionFactor(Double underwaterExplosionFactor) {
        this.underwaterExplosionFactor = underwaterExplosionFactor;
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
        result = prime * result + (explosionDamageBlocksUnderwater ? 1231 : 1237);
        result = prime * result + ((particleConfiguration == null) ? 0 : particleConfiguration.hashCode());
        result = prime * result + ((soundConfiguration == null) ? 0 : soundConfiguration.hashCode());
        result = prime * result + ((underwaterExplosionFactor == null) ? 0 : underwaterExplosionFactor.hashCode());
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
        if (explosionDamageBlocksUnderwater != other.explosionDamageBlocksUnderwater)
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
        if (underwaterExplosionFactor == null) {
            if (other.underwaterExplosionFactor != null)
                return false;
        } else if (!underwaterExplosionFactor.equals(other.underwaterExplosionFactor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EntityConfiguration [explosionDamageBlocksUnderwater=" + explosionDamageBlocksUnderwater
                + ", underwaterExplosionFactor=" + underwaterExplosionFactor + ", soundConfiguration="
                + soundConfiguration + ", particleConfiguration=" + particleConfiguration + "]";
    }
}
