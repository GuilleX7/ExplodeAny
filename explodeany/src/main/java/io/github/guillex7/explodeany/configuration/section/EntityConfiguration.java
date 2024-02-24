package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.util.MathUtils;

public class EntityConfiguration {
    private static final String EXPLOSION_RADIUS_PATH = "ExplosionRadius";
    private static final String EXPLOSION_FACTOR_PATH = "ExplosionFactor";
    private static final String REPLACE_ORIGINAL_EXPLOSION_PATH = "ReplaceOriginalExplosion";
    private static final String UNDERWATER_EXPLOSION_FACTOR_PATH = "UnderwaterExplosionFactor";
    private static final String EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH = "ExplosionDamageBlocksUnderwater";
    private static final String REPLACE_ORIGINAL_EXPLOSION_WHEN_UNDERWATER_PATH = "ReplaceOriginalExplosionWhenUnderwater";
    private static final String PACK_DROPPED_ITEMS_PATH = "PackDroppedItems";

    private final double explosionRadius;
    private final double explosionFactor;
    private final boolean replaceOriginalExplosion;
    private final double underwaterExplosionFactor;
    private final boolean explosionDamageBlocksUnderwater;
    private final boolean replaceOriginalExplosionWhenUnderwater;
    private final boolean packDroppedItems;
    private final EntityBehavioralConfiguration entityBehavioralConfiguration;
    private final SoundConfiguration soundConfiguration;
    private final ParticleConfiguration particleConfiguration;

    public static EntityConfiguration byDefault() {
        return new EntityConfiguration(0.0d,
                1.0d,
                false,
                0.5d,
                false,
                true,
                false,
                EntityBehavioralConfiguration.byDefault(),
                SoundConfiguration.byDefault(),
                ParticleConfiguration.byDefault());
    }

    public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityConfiguration defaults = EntityConfiguration.byDefault();
        ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SoundConfiguration.ROOT_PATH);
        ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(ParticleConfiguration.ROOT_PATH);

        return new EntityConfiguration(
                MathUtils.ensureMin(section.getDouble(EXPLOSION_RADIUS_PATH, defaults.getExplosionRadius()), 0.0d),
                MathUtils.ensureMin(section.getDouble(EXPLOSION_FACTOR_PATH, defaults.getExplosionFactor()), 0.0d),
                section.getBoolean(REPLACE_ORIGINAL_EXPLOSION_PATH, defaults.doReplaceOriginalExplosion()),
                MathUtils.ensureMin(
                        section.getDouble(UNDERWATER_EXPLOSION_FACTOR_PATH, defaults.getUnderwaterExplosionFactor()),
                        0.0d),
                section.getBoolean(EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionDamageBlocksUnderwater()),
                section.getBoolean(REPLACE_ORIGINAL_EXPLOSION_WHEN_UNDERWATER_PATH,
                        defaults.doReplaceOriginalExplosionWhenUnderwater()),
                section.getBoolean(PACK_DROPPED_ITEMS_PATH, defaults.doPackDroppedItems()),
                EntityBehavioralConfiguration.fromConfigurationSection(section),
                (soundConfigurationSection != null)
                        ? SoundConfiguration.fromConfigurationSection(soundConfigurationSection)
                        : SoundConfiguration.byDefault(),
                (particleConfigurationSection != null)
                        ? ParticleConfiguration.fromConfigurationSection(particleConfigurationSection)
                        : ParticleConfiguration.byDefault());
    }

    public EntityConfiguration(double explosionRadius, double explosionFactor, boolean replaceOriginalExplosion,
            double underwaterExplosionFactor, boolean explosionDamageBlocksUnderwater,
            boolean replaceOriginalExplosionWhenUnderwater, boolean packDroppedItems,
            EntityBehavioralConfiguration entityBehavioralConfiguration,
            SoundConfiguration soundConfiguration,
            ParticleConfiguration particleConfiguration) {
        this.explosionRadius = explosionRadius;
        this.explosionFactor = explosionFactor;
        this.replaceOriginalExplosion = replaceOriginalExplosion;
        this.underwaterExplosionFactor = underwaterExplosionFactor;
        this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
        this.replaceOriginalExplosionWhenUnderwater = replaceOriginalExplosionWhenUnderwater;
        this.packDroppedItems = packDroppedItems;
        this.entityBehavioralConfiguration = entityBehavioralConfiguration;
        this.soundConfiguration = soundConfiguration;
        this.particleConfiguration = particleConfiguration;
    }

    public double getExplosionRadius() {
        return explosionRadius;
    }

    public double getExplosionFactor() {
        return explosionFactor;
    }

    public boolean doReplaceOriginalExplosion() {
        return replaceOriginalExplosion;
    }

    public double getUnderwaterExplosionFactor() {
        return underwaterExplosionFactor;
    }

    public boolean doesExplosionDamageBlocksUnderwater() {
        return explosionDamageBlocksUnderwater;
    }

    public boolean doReplaceOriginalExplosionWhenUnderwater() {
        return replaceOriginalExplosionWhenUnderwater;
    }

    public boolean doPackDroppedItems() {
        return packDroppedItems;
    }

    public EntityBehavioralConfiguration getEntityBehavioralConfiguration() {
        return entityBehavioralConfiguration;
    }

    public SoundConfiguration getSoundConfiguration() {
        return soundConfiguration;
    }

    public ParticleConfiguration getParticleConfiguration() {
        return particleConfiguration;
    }

    @Override
    public String toString() {
        return String.format(
                "&7<General>\n"
                        + "&fExplosion radius: %.2f\n"
                        + "&fExplosion factor: %.2f\n"
                        + "&fReplace original explosion: %b\n"
                        + "&fUnderwater explosion factor: %.2f\n"
                        + "&fDamage blocks underwater: %b\n"
                        + "&fReplace underwater original explosion: %b\n"
                        + "&fPack dropped items: %b\n"
                        + "\n&7<Behavior>\n"
                        + "&f%s\n"
                        + "\n&7<Sound>\n"
                        + "&f%s\n"
                        + "\n&7<Particle>\n"
                        + "&f%s",
                this.getExplosionRadius(), this.getExplosionFactor(), this.doReplaceOriginalExplosion(),
                this.getUnderwaterExplosionFactor(), this.doesExplosionDamageBlocksUnderwater(),
                this.doReplaceOriginalExplosionWhenUnderwater(), this.doPackDroppedItems(),
                this.getEntityBehavioralConfiguration().toString(), this.getSoundConfiguration().toString(),
                this.getParticleConfiguration().toString());
    }
}
