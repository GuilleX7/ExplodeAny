package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class EntityMaterialConfiguration {
    private static final String DAMAGE_PATH = "Damage";
    private static final String DROP_CHANCE_PATH = "DropChance";
    private static final String DROP_MATERIAL_PATH = "DropMaterial";
    private static final String DISTANCE_ATTENUATION_FACTOR_PATH = "DistanceAttenuationFactor";
    private static final String UNDERWATER_DAMAGE_FACTOR_PATH = "UnderwaterDamageFactor";
    private static final String FANCY_UNDERWATER_DETECTION_PATH = "FancyUnderwaterDetection";
    private static final String SOUND_PATH = "Sound";
    private static final String PARTICLES_PATH = "Particles";

    private final double damage;
    private final double dropChance;
    private final Material dropMaterial;
    private final double distanceAttenuationFactor;
    private final double underwaterDamageFactor;
    private final boolean fancyUnderwaterDetection;
    private final SoundConfiguration onBreakSoundConfiguration;
    private final SoundConfiguration onHitSoundConfiguration;
    private final ParticleConfiguration onBreakParticleConfiguration;
    private final ParticleConfiguration onHitParticleConfiguration;

    public static EntityMaterialConfiguration byDefault() {
        return new EntityMaterialConfiguration(
                ConfigurationManager.getInstance().getGlobalBlockDurability(),
                0.0d,
                null,
                0.0d,
                0.5d,
                false,
                SoundConfiguration.byDefault(),
                SoundConfiguration.byDefault(),
                ParticleConfiguration.byDefault(),
                ParticleConfiguration.byDefault());
    }

    public static EntityMaterialConfiguration fromConfigurationSection(final ConfigurationSection section) {
        final EntityMaterialConfiguration defaults = EntityMaterialConfiguration.byDefault();

        Material dropMaterial;
        String dropMaterialString = "";
        try {
            dropMaterialString = section.getString(EntityMaterialConfiguration.DROP_MATERIAL_PATH, "");
            dropMaterial = Material.valueOf(dropMaterialString.toUpperCase());
            // Hint: some materials are not valid for ItemStack
            new ItemStack(dropMaterial, 1);
        } catch (final Exception e) {
            if (!"".equals(dropMaterialString)) {
                ExplodeAny.getInstance().getLogger()
                        .warning(String.format(
                                "Invalid drop material '%s' in configuration section '%s'. Using default value.",
                                section.getString(EntityMaterialConfiguration.DROP_MATERIAL_PATH),
                                section.getCurrentPath()));
            }
            dropMaterial = defaults.getDropMaterial();
        }

        final ConfigurationSection soundConfigurationSection = section
                .getConfigurationSection(EntityMaterialConfiguration.SOUND_PATH);
        SoundConfiguration onBreakSoundConfiguration = SoundConfiguration.byDefault();
        SoundConfiguration onHitSoundConfiguration = SoundConfiguration.byDefault();
        if (soundConfigurationSection != null) {
            boolean hasSoundSections = false;
            if (soundConfigurationSection.getConfigurationSection("OnBreak") != null) {
                onBreakSoundConfiguration = SoundConfiguration.fromConfigurationSection(
                        soundConfigurationSection.getConfigurationSection("OnBreak"));
                hasSoundSections = true;
            }
            if (soundConfigurationSection.getConfigurationSection("OnHit") != null) {
                onHitSoundConfiguration = SoundConfiguration.fromConfigurationSection(
                        soundConfigurationSection.getConfigurationSection("OnHit"));
                hasSoundSections = true;
            }
            if (!hasSoundSections) {
                onBreakSoundConfiguration = SoundConfiguration.fromConfigurationSection(soundConfigurationSection);
            }
        }

        final ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(EntityMaterialConfiguration.PARTICLES_PATH);
        ParticleConfiguration onBreakParticleConfiguration = ParticleConfiguration.byDefault();
        ParticleConfiguration onHitParticleConfiguration = ParticleConfiguration.byDefault();
        if (particleConfigurationSection != null) {
            boolean hasParticleSections = false;
            if (particleConfigurationSection.getConfigurationSection("OnBreak") != null) {
                onBreakParticleConfiguration = ParticleConfiguration.fromConfigurationSection(
                        particleConfigurationSection.getConfigurationSection("OnBreak"));
                hasParticleSections = true;
            }
            if (particleConfigurationSection.getConfigurationSection("OnHit") != null) {
                onHitParticleConfiguration = ParticleConfiguration.fromConfigurationSection(
                        particleConfigurationSection.getConfigurationSection("OnHit"));
                hasParticleSections = true;
            }
            if (!hasParticleSections) {
                onBreakParticleConfiguration = ParticleConfiguration
                        .fromConfigurationSection(particleConfigurationSection);
            }
        }

        return new EntityMaterialConfiguration(
                MathUtils.ensureMin(
                        section.getDouble(EntityMaterialConfiguration.DAMAGE_PATH, defaults.getDamage()), 0.0d),
                MathUtils.ensureRange(
                        section.getDouble(EntityMaterialConfiguration.DROP_CHANCE_PATH, defaults.getDropChance()),
                        100.0d,
                        0.0d) / 100.0d,
                dropMaterial,
                MathUtils.ensureRange(
                        section.getDouble(EntityMaterialConfiguration.DISTANCE_ATTENUATION_FACTOR_PATH,
                                defaults.getDistanceAttenuationFactor()),
                        1.0d, 0.0d),
                MathUtils.ensureMin(
                        section.getDouble(EntityMaterialConfiguration.UNDERWATER_DAMAGE_FACTOR_PATH,
                                defaults.getUnderwaterDamageFactor()),
                        0.0d),
                section.getBoolean(
                        EntityMaterialConfiguration.FANCY_UNDERWATER_DETECTION_PATH,
                        defaults.isFancyUnderwaterDetection()),
                onBreakSoundConfiguration,
                onHitSoundConfiguration,
                onBreakParticleConfiguration,
                onHitParticleConfiguration);
    }

    public EntityMaterialConfiguration(final double damage, final double dropChance, final Material dropMaterial,
            final double distanceAttenuationFactor,
            final double underwaterDamageFactor, final boolean fancyUnderwaterDetection,
            final SoundConfiguration onBreakSoundConfiguration, final SoundConfiguration onHitSoundConfiguration,
            final ParticleConfiguration onBreakParticleConfiguration,
            final ParticleConfiguration onHitParticleConfiguration) {
        this.damage = damage;
        this.dropChance = dropChance;
        this.dropMaterial = dropMaterial;
        this.distanceAttenuationFactor = distanceAttenuationFactor;
        this.underwaterDamageFactor = underwaterDamageFactor;
        this.fancyUnderwaterDetection = fancyUnderwaterDetection;
        this.onBreakSoundConfiguration = onBreakSoundConfiguration;
        this.onHitSoundConfiguration = onHitSoundConfiguration;
        this.onBreakParticleConfiguration = onBreakParticleConfiguration;
        this.onHitParticleConfiguration = onHitParticleConfiguration;
    }

    public double getDamage() {
        return this.damage;
    }

    public double getDropChance() {
        return this.dropChance;
    }

    public double getDropChancePercentage() {
        return this.getDropChance() * 100;
    }

    public boolean shouldBeDropped() {
        return Math.random() < this.getDropChance();
    }

    public Material getDropMaterial() {
        return this.dropMaterial;
    }

    public double getDistanceAttenuationFactor() {
        return this.distanceAttenuationFactor;
    }

    public double getUnderwaterDamageFactor() {
        return this.underwaterDamageFactor;
    }

    public boolean isUnderwaterAffected() {
        return this.getUnderwaterDamageFactor() != 1.0;
    }

    public boolean isFancyUnderwaterDetection() {
        return this.fancyUnderwaterDetection;
    }

    public SoundConfiguration getOnBreakSoundConfiguration() {
        return this.onBreakSoundConfiguration;
    }

    public ParticleConfiguration getOnBreakParticleConfiguration() {
        return this.onBreakParticleConfiguration;
    }

    public SoundConfiguration getOnHitSoundConfiguration() {
        return this.onHitSoundConfiguration;
    }

    public ParticleConfiguration getOnHitParticleConfiguration() {
        return this.onHitParticleConfiguration;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("&7<General>\n");
        builder.append("&fDamage: ").append(String.format("%.2f", this.getDamage())).append("\n");
        builder.append("&fDrop chance: ").append(String.format("%.2f", this.getDropChancePercentage())).append("%\n");
        builder.append("&fDrop material: ")
                .append(this.getDropMaterial() == null ? "(not customized)" : this.getDropMaterial().toString())
                .append("\n");
        builder.append("&fDistance attenuation factor: x")
                .append(String.format("%.2f", this.getDistanceAttenuationFactor())).append("\n");
        builder.append("&fUnderwater damage factor: x").append(String.format("%.2f", this.getUnderwaterDamageFactor()))
                .append("\n");
        builder.append("&fFancy underwater detection: ").append(this.isFancyUnderwaterDetection()).append("\n");
        builder.append("\n&7<Sound: on break>\n");
        builder.append("&f").append(this.getOnBreakSoundConfiguration().toString()).append("\n");
        builder.append("\n&7<Sound: on hit>\n");
        builder.append("&f").append(this.getOnHitSoundConfiguration().toString()).append("\n");
        builder.append("\n&7<Particle: on break>\n");
        builder.append("&f").append(this.getOnBreakParticleConfiguration().toString());
        builder.append("\n&7<Particle: on hit>\n");
        builder.append("&f").append(this.getOnHitParticleConfiguration().toString());
        return builder.toString();
    }
}
