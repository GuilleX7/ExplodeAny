package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.section.specific.SpecificEntityConfiguration;
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
    private SpecificEntityConfiguration specificConfiguration = null;

    public static EntityConfiguration byDefault() {
        return new EntityConfiguration(/* explosionRadius */ 0.0d,
                /* explosionFactor */ 1.0d,
                /* replaceOriginalExplosion */ false,
                /* underwaterExplosionFactor */ 0.5d,
                /* explosionDamageBlocksUnderwater */ false,
                /* replaceOriginalExplosionWhenUnderwater */ true,
                /* packDroppedItems */ false,
                /* entityBehavioralConfiguration */ EntityBehavioralConfiguration.byDefault(),
                /* soundConfiguration */ SoundConfiguration.byDefault(),
                /* particleConfiguration */ ParticleConfiguration.byDefault());
    }

    public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityConfiguration defaults = EntityConfiguration.byDefault();
        ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SoundConfiguration.ROOT_PATH);
        ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(ParticleConfiguration.ROOT_PATH);

        return new EntityConfiguration(
                // explosionRadius
                MathUtils.ensureMin(section.getDouble(EXPLOSION_RADIUS_PATH, defaults.getExplosionRadius()), 0.0d),
                // explosionFactor
                MathUtils.ensureMin(section.getDouble(EXPLOSION_FACTOR_PATH, defaults.getExplosionFactor()), 0.0d),
                // replaceOriginalExplosion
                section.getBoolean(REPLACE_ORIGINAL_EXPLOSION_PATH, defaults.doReplaceOriginalExplosion()),
                // underwaterExplosionFactor
                MathUtils.ensureMin(
                        section.getDouble(UNDERWATER_EXPLOSION_FACTOR_PATH, defaults.getUnderwaterExplosionFactor()),
                        0.0d),
                // explosionDamageBlocksUnderwater
                section.getBoolean(EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionDamageBlocksUnderwater()),
                // replaceOriginalExplosionWhenUnderwater
                section.getBoolean(REPLACE_ORIGINAL_EXPLOSION_WHEN_UNDERWATER_PATH,
                        defaults.doReplaceOriginalExplosionWhenUnderwater()),
                // packDroppedItems
                section.getBoolean(PACK_DROPPED_ITEMS_PATH, defaults.doPackDroppedItems()),
                // entityBehavioralConfiguration
                EntityBehavioralConfiguration.fromConfigurationSection(section),
                // soundConfiguration
                (soundConfigurationSection != null)
                        ? SoundConfiguration.fromConfigurationSection(soundConfigurationSection)
                        : SoundConfiguration.byDefault(),
                // particleConfiguration
                (particleConfigurationSection != null)
                        ? ParticleConfiguration.fromConfigurationSection(particleConfigurationSection)
                        : ParticleConfiguration.byDefault());
    }

    public EntityConfiguration(double explosionRadius, double explosionFactor, boolean replaceOriginalExplosion,
            double underwaterExplosionFactor, boolean explosionDamageBlocksUnderwater,
            boolean replaceOriginalExplosionWhenUnderwater,
            boolean packDroppedItems, EntityBehavioralConfiguration entityBehavioralConfiguration,
            SoundConfiguration soundConfiguration, ParticleConfiguration particleConfiguration) {
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

    public boolean isExplosionRadiusOverridden() {
        return this.getExplosionRadius() > 0.0d;
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

    public SpecificEntityConfiguration getSpecificConfiguration() {
        return specificConfiguration;
    }

    public void setSpecificConfiguration(SpecificEntityConfiguration specificConfiguration) {
        this.specificConfiguration = specificConfiguration;
    }

    @Override
    public String toString() {
        final String explosionRadiusString = (this.isExplosionRadiusOverridden())
                ? String.format("%.2f blocks", this.getExplosionRadius())
                : "default";

        StringBuilder builder = new StringBuilder();
        builder.append("&7<General>\n");
        builder.append("&fExplosion radius: ").append(explosionRadiusString).append("\n");
        builder.append("&fExplosion factor: x").append(String.format("%.2f", this.getExplosionFactor())).append("\n");
        builder.append("&fReplace original explosion: ").append(this.doReplaceOriginalExplosion()).append("\n");
        builder.append("&fUnderwater explosion factor: x")
                .append(String.format("%.2f", this.getUnderwaterExplosionFactor())).append("\n");
        builder.append("&fDamage blocks underwater: ").append(this.doesExplosionDamageBlocksUnderwater()).append("\n");
        builder.append("&fReplace underwater original explosion: ")
                .append(this.doReplaceOriginalExplosionWhenUnderwater()).append("\n");
        builder.append("&fPack dropped items: ").append(this.doPackDroppedItems()).append("\n");
        if (this.specificConfiguration != null) {
            builder.append("\n&7<Specific>\n");
            builder.append(this.specificConfiguration.toString());
        }
        builder.append("\n&7<Behavior>\n");
        builder.append("&f").append(this.getEntityBehavioralConfiguration().toString()).append("\n");
        builder.append("\n&7<Sound>\n");
        builder.append("&f").append(this.getSoundConfiguration().toString()).append("\n");
        builder.append("\n&7<Particle>\n");
        builder.append("&f").append(this.getParticleConfiguration().toString());
        return builder.toString();
    }

    public EntityConfiguration clone() {
        return new EntityConfiguration(this.explosionRadius, this.explosionFactor, this.replaceOriginalExplosion,
                this.underwaterExplosionFactor, this.explosionDamageBlocksUnderwater,
                this.replaceOriginalExplosionWhenUnderwater, this.packDroppedItems,
                this.entityBehavioralConfiguration.clone(), this.soundConfiguration.clone(),
                this.particleConfiguration.clone());
    }
}
