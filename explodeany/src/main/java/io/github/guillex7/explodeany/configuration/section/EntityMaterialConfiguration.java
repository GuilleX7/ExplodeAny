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
                /* damage */ ConfigurationManager.getInstance().getGlobalBlockDurability(),
                /* dropChance */ 0.0d,
                /* dropMaterial */ null,
                /* distanceAttenuationFactor */ 0.0d,
                /* underwaterDamageFactor */ 0.5d,
                /* fancyUnderwaterDetection */ false,
                /* onBreakSoundConfiguration */ SoundConfiguration.byDefault(),
                /* onHitSoundConfiguration */ SoundConfiguration.byDefault(),
                /* onBreakParticleConfiguration */ ParticleConfiguration.byDefault(),
                /* onHitParticleConfiguration */ ParticleConfiguration.byDefault());
    }

    public static EntityMaterialConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityMaterialConfiguration defaults = EntityMaterialConfiguration.byDefault();

        Material dropMaterial;
        String dropMaterialString = "";
        try {
            dropMaterialString = section.getString(DROP_MATERIAL_PATH, "");
            dropMaterial = Material.valueOf(dropMaterialString.toUpperCase());
            // Hint: some materials are not valid for ItemStack
            @SuppressWarnings("unused")
            ItemStack item = new ItemStack(dropMaterial, 1);
        } catch (Exception e) {
            if (!dropMaterialString.equals("")) {
                ExplodeAny.getInstance().getLogger()
                        .warning(String.format(
                                "Invalid drop material '%s' in configuration section '%s'. Using default value.",
                                section.getString(DROP_MATERIAL_PATH), section.getCurrentPath()));
            }
            dropMaterial = defaults.getDropMaterial();
        }

        ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SOUND_PATH);
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

        ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(PARTICLES_PATH);
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
                /* damage */ MathUtils.ensureMin(section.getDouble(DAMAGE_PATH, defaults.getDamage()), 0.0d),
                /* dropChance */ MathUtils.ensureRange(section.getDouble(DROP_CHANCE_PATH, defaults.getDropChance()),
                        100.0d,
                        0.0d) / 100.0d,
                /* dropMaterial */ dropMaterial,
                /* distanceAttenuationFactor */ MathUtils.ensureRange(
                        section.getDouble(DISTANCE_ATTENUATION_FACTOR_PATH, defaults.getDistanceAttenuationFactor()),
                        1.0d, 0.0d),
                /* underwaterDamageFactor */ MathUtils.ensureMin(
                        section.getDouble(UNDERWATER_DAMAGE_FACTOR_PATH, defaults.getUnderwaterDamageFactor()), 0.0d),
                /* fancyUnderwaterDetection */ section.getBoolean(FANCY_UNDERWATER_DETECTION_PATH,
                        defaults.isFancyUnderwaterDetection()),
                /* onBreakSoundConfiguration */ onBreakSoundConfiguration,
                /* onHitSoundConfiguration */ onHitSoundConfiguration,
                /* onBreakParticleConfiguration */ onBreakParticleConfiguration,
                /* onHitParticleConfiguration */ onHitParticleConfiguration);
    }

    public EntityMaterialConfiguration(double damage, double dropChance, Material dropMaterial,
            double distanceAttenuationFactor,
            double underwaterDamageFactor, boolean fancyUnderwaterDetection,
            SoundConfiguration onBreakSoundConfiguration, SoundConfiguration onHitSoundConfiguration,
            ParticleConfiguration onBreakParticleConfiguration, ParticleConfiguration onHitParticleConfiguration) {
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
        return damage;
    }

    public double getDropChance() {
        return dropChance;
    }

    public double getDropChancePercentage() {
        return this.getDropChance() * 100;
    }

    public boolean shouldBeDropped() {
        return Math.random() < this.getDropChance();
    }

    public Material getDropMaterial() {
        return dropMaterial;
    }

    public double getDistanceAttenuationFactor() {
        return distanceAttenuationFactor;
    }

    public double getUnderwaterDamageFactor() {
        return underwaterDamageFactor;
    }

    public boolean isUnderwaterAffected() {
        return this.getUnderwaterDamageFactor() != 1.0;
    }

    public boolean isFancyUnderwaterDetection() {
        return fancyUnderwaterDetection;
    }

    public SoundConfiguration getOnBreakSoundConfiguration() {
        return onBreakSoundConfiguration;
    }

    public ParticleConfiguration getOnBreakParticleConfiguration() {
        return onBreakParticleConfiguration;
    }

    public SoundConfiguration getOnHitSoundConfiguration() {
        return onHitSoundConfiguration;
    }

    public ParticleConfiguration getOnHitParticleConfiguration() {
        return onHitParticleConfiguration;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("&7<General>\n");
        builder.append("&fDamage: ").append(String.format("%.2f", this.getDamage())).append("\n");
        builder.append("&fDrop chance: ").append(String.format("%.2f", this.getDropChancePercentage())).append("%%\n");
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
