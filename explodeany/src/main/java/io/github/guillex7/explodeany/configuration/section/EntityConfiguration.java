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
    private static final String SOUND_PATH = "Sound";
    private static final String PARTICLES_PATH = "Particles";

    private final double explosionRadius;
    private final double explosionFactor;
    private final boolean replaceOriginalExplosion;
    private final double underwaterExplosionFactor;
    private final boolean explosionDamageBlocksUnderwater;
    private final boolean replaceOriginalExplosionWhenUnderwater;
    private final boolean packDroppedItems;
    private final EntityBehavioralConfiguration entityBehavioralConfiguration;
    private final SoundConfiguration onExplodeSoundConfiguration;
    private final ParticleConfiguration onExplodeParticleConfiguration;
    private SpecificEntityConfiguration specificConfiguration = null;

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

    public static EntityConfiguration fromConfigurationSection(final ConfigurationSection section) {
        final EntityConfiguration defaults = EntityConfiguration.byDefault();

        final ConfigurationSection soundConfigurationSection = section
                .getConfigurationSection(EntityConfiguration.SOUND_PATH);
        SoundConfiguration onExplodeSoundConfiguration = SoundConfiguration.byDefault();
        if (soundConfigurationSection != null) {
            boolean hasSoundSections = false;
            if (soundConfigurationSection.getConfigurationSection("OnExplode") != null) {
                onExplodeSoundConfiguration = SoundConfiguration.fromConfigurationSection(
                        soundConfigurationSection.getConfigurationSection("OnExplode"));
                hasSoundSections = true;
            }
            if (!hasSoundSections) {
                onExplodeSoundConfiguration = SoundConfiguration.fromConfigurationSection(soundConfigurationSection);
            }
        }

        final ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(EntityConfiguration.PARTICLES_PATH);
        ParticleConfiguration onExplodeParticleConfiguration = ParticleConfiguration.byDefault();
        if (particleConfigurationSection != null) {
            boolean hasParticleSections = false;
            if (particleConfigurationSection.getConfigurationSection("OnExplode") != null) {
                onExplodeParticleConfiguration = ParticleConfiguration.fromConfigurationSection(
                        particleConfigurationSection.getConfigurationSection("OnExplode"));
                hasParticleSections = true;
            }
            if (!hasParticleSections) {
                onExplodeParticleConfiguration = ParticleConfiguration
                        .fromConfigurationSection(particleConfigurationSection);
            }
        }

        return new EntityConfiguration(
                MathUtils.ensureMin(
                        section.getDouble(EntityConfiguration.EXPLOSION_RADIUS_PATH, defaults.getExplosionRadius()),
                        0.0d),
                MathUtils.ensureMin(
                        section.getDouble(EntityConfiguration.EXPLOSION_FACTOR_PATH, defaults.getExplosionFactor()),
                        0.0d),
                section.getBoolean(EntityConfiguration.REPLACE_ORIGINAL_EXPLOSION_PATH,
                        defaults.doReplaceOriginalExplosion()),
                MathUtils.ensureMin(
                        section.getDouble(EntityConfiguration.UNDERWATER_EXPLOSION_FACTOR_PATH,
                                defaults.getUnderwaterExplosionFactor()),
                        0.0d),
                section.getBoolean(EntityConfiguration.EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH,
                        defaults.doesExplosionDamageBlocksUnderwater()),
                section.getBoolean(EntityConfiguration.REPLACE_ORIGINAL_EXPLOSION_WHEN_UNDERWATER_PATH,
                        defaults.doReplaceOriginalExplosionWhenUnderwater()),
                section.getBoolean(EntityConfiguration.PACK_DROPPED_ITEMS_PATH, defaults.doPackDroppedItems()),
                EntityBehavioralConfiguration.fromConfigurationSection(section),
                onExplodeSoundConfiguration,
                onExplodeParticleConfiguration);
    }

    public EntityConfiguration(final double explosionRadius, final double explosionFactor,
            final boolean replaceOriginalExplosion,
            final double underwaterExplosionFactor, final boolean explosionDamageBlocksUnderwater,
            final boolean replaceOriginalExplosionWhenUnderwater,
            final boolean packDroppedItems, final EntityBehavioralConfiguration entityBehavioralConfiguration,
            final SoundConfiguration onExplodeSoundConfiguration,
            final ParticleConfiguration onExplodeParticleConfiguration) {
        this.explosionRadius = explosionRadius;
        this.explosionFactor = explosionFactor;
        this.replaceOriginalExplosion = replaceOriginalExplosion;
        this.underwaterExplosionFactor = underwaterExplosionFactor;
        this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
        this.replaceOriginalExplosionWhenUnderwater = replaceOriginalExplosionWhenUnderwater;
        this.packDroppedItems = packDroppedItems;
        this.entityBehavioralConfiguration = entityBehavioralConfiguration;
        this.onExplodeSoundConfiguration = onExplodeSoundConfiguration;
        this.onExplodeParticleConfiguration = onExplodeParticleConfiguration;
    }

    public double getExplosionRadius() {
        return this.explosionRadius;
    }

    public boolean isExplosionRadiusOverridden() {
        return this.getExplosionRadius() > 0.0d;
    }

    public double getExplosionFactor() {
        return this.explosionFactor;
    }

    public boolean doReplaceOriginalExplosion() {
        return this.replaceOriginalExplosion;
    }

    public double getUnderwaterExplosionFactor() {
        return this.underwaterExplosionFactor;
    }

    public boolean doesExplosionDamageBlocksUnderwater() {
        return this.explosionDamageBlocksUnderwater;
    }

    public boolean doReplaceOriginalExplosionWhenUnderwater() {
        return this.replaceOriginalExplosionWhenUnderwater;
    }

    public boolean doPackDroppedItems() {
        return this.packDroppedItems;
    }

    public EntityBehavioralConfiguration getEntityBehavioralConfiguration() {
        return this.entityBehavioralConfiguration;
    }

    public SoundConfiguration getOnExplodeSoundConfiguration() {
        return this.onExplodeSoundConfiguration;
    }

    public ParticleConfiguration getOnExplodeParticleConfiguration() {
        return this.onExplodeParticleConfiguration;
    }

    public SpecificEntityConfiguration getSpecificConfiguration() {
        return this.specificConfiguration;
    }

    public void setSpecificConfiguration(final SpecificEntityConfiguration specificConfiguration) {
        this.specificConfiguration = specificConfiguration;
    }

    @Override
    public String toString() {
        final String explosionRadiusString = (this.isExplosionRadiusOverridden())
                ? String.format("%.2f blocks", this.getExplosionRadius())
                : "default";

        final StringBuilder builder = new StringBuilder();
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
        builder.append("\n&7<Sound: on explode>\n");
        builder.append("&f").append(this.getOnExplodeSoundConfiguration().toString()).append("\n");
        builder.append("\n&7<Particle: on explode>\n");
        builder.append("&f").append(this.getOnExplodeParticleConfiguration().toString());
        return builder.toString();
    }

    @Override
    public EntityConfiguration clone() {
        return new EntityConfiguration(this.explosionRadius, this.explosionFactor, this.replaceOriginalExplosion,
                this.underwaterExplosionFactor, this.explosionDamageBlocksUnderwater,
                this.replaceOriginalExplosionWhenUnderwater, this.packDroppedItems,
                this.entityBehavioralConfiguration.clone(), this.onExplodeSoundConfiguration.clone(),
                this.onExplodeParticleConfiguration.clone());
    }
}
