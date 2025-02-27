package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class EntityMaterialConfiguration {
    private static final String DAMAGE_PATH = "Damage";
    private static final String DROP_CHANCE_PATH = "DropChance";
    private static final String DROP_MATERIAL_PATH = "DropMaterial";
    private static final String DISTANCE_ATTENUATION_FACTOR_PATH = "DistanceAttenuationFactor";
    private static final String UNDERWATER_DAMAGE_FACTOR_PATH = "UnderwaterDamageFactor";
    private static final String FANCY_UNDERWATER_DETECTION_PATH = "FancyUnderwaterDetection";

    private final double damage;
    private final double dropChance;
    private final Material dropMaterial;
    private final double distanceAttenuationFactor;
    private final double underwaterDamageFactor;
    private final boolean fancyUnderwaterDetection;
    private final SoundConfiguration soundConfiguration;
    private final ParticleConfiguration particleConfiguration;

    public static EntityMaterialConfiguration byDefault() {
        return new EntityMaterialConfiguration(ConfigurationManager.getInstance().getGlobalBlockDurability(), 0.0d,
                null,
                0.0d,
                0.5d, false, SoundConfiguration.byDefault(), ParticleConfiguration.byDefault());
    }

    public static EntityMaterialConfiguration fromConfigurationSection(ConfigurationSection section) {
        EntityMaterialConfiguration defaults = EntityMaterialConfiguration.byDefault();
        ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SoundConfiguration.ROOT_PATH);
        ConfigurationSection particleConfigurationSection = section
                .getConfigurationSection(ParticleConfiguration.ROOT_PATH);

        Material dropMaterial;
        try {
            dropMaterial = Material.valueOf(section.getString(DROP_MATERIAL_PATH, "").toUpperCase());
        } catch (IllegalArgumentException e) {
            dropMaterial = defaults.getDropMaterial();
        }

        return new EntityMaterialConfiguration(
                MathUtils.ensureMin(section.getDouble(DAMAGE_PATH, defaults.getDamage()), 0.0d),
                MathUtils.ensureRange(section.getDouble(DROP_CHANCE_PATH, defaults.getDropChance()), 100.0d,
                        0.0d) / 100.0d,
                dropMaterial,
                MathUtils.ensureRange(
                        section.getDouble(DISTANCE_ATTENUATION_FACTOR_PATH, defaults.getDistanceAttenuationFactor()),
                        1.0d, 0.0d),
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

    public EntityMaterialConfiguration(double damage, double dropChance, Material dropMaterial,
            double distanceAttenuationFactor,
            double underwaterDamageFactor, boolean fancyUnderwaterDetection,
            SoundConfiguration soundConfiguration, ParticleConfiguration particleConfiguration) {
        this.damage = damage;
        this.dropChance = dropChance;
        this.dropMaterial = dropMaterial;
        this.distanceAttenuationFactor = distanceAttenuationFactor;
        this.underwaterDamageFactor = underwaterDamageFactor;
        this.fancyUnderwaterDetection = fancyUnderwaterDetection;
        this.soundConfiguration = soundConfiguration;
        this.particleConfiguration = particleConfiguration;
    }

    public double getDamage() {
        return damage;
    }

    public double getDropChance() {
        return dropChance;
    }

    public int getDropChancePercentage() {
        return (int) (this.getDropChance() * 100);
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
                        + "&fDamage: %.2f\n"
                        + "&fDrop chance: %d%%\n"
                        + "&fDrop material: %s\n"
                        + "&fDistance attenuation factor: x%.2f\n"
                        + "&fUnderwater damage factor: x%.2f\n"
                        + "&fFancy underwater detection: %b\n"
                        + "\n&7<Sound>\n"
                        + "&f%s\n"
                        + "\n&7<Particle>\n"
                        + "&f%s",
                this.getDamage(), this.getDropChancePercentage(),
                this.getDropMaterial() == null ? "(not customized)" : this.getDropMaterial().toString(),
                this.getDistanceAttenuationFactor(),
                this.getUnderwaterDamageFactor(), this.isFancyUnderwaterDetection(),
                this.getSoundConfiguration().toString(), this.getParticleConfiguration().toString());
    }
}
