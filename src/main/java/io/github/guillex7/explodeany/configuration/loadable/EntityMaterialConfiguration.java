package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;

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
	
	public static EntityMaterialConfiguration of(double damage, double dropChance, double distanceAttenuationFactor,
			double explosionRadiusFactor, double underwaterDamageFactor, boolean fancyUnderwaterDetection, SoundConfiguration soundConfiguration) {
		return new EntityMaterialConfiguration(damage, dropChance, distanceAttenuationFactor, explosionRadiusFactor,
				underwaterDamageFactor, fancyUnderwaterDetection, soundConfiguration);
	}
	
	public static EntityMaterialConfiguration byDefault() {
		return EntityMaterialConfiguration.of(ConfigurationManager.getInstance().getBlockDurability(), 0.0d, 0.0d, 0.5d, 0.5d, false, SoundConfiguration.byDefault());
	}

	public static EntityMaterialConfiguration fromConfigurationSection(ConfigurationSection section) {
		EntityMaterialConfiguration defaults = EntityMaterialConfiguration.byDefault();
		ConfigurationSection soundConfigurationSection = section.getConfigurationSection(SoundConfiguration.ROOT_PATH);
		
		return EntityMaterialConfiguration.of(
				ConfigurationManager.ensureMin(
						section.getDouble(DAMAGE_PATH, defaults.getDamage()), 0.0d),
				ConfigurationManager.ensureRange(section.getDouble(DROP_CHANCE_PATH, defaults.getDropChance()), 100.0d, 0.0d) / 100.0,
				ConfigurationManager.ensureRange(section.getDouble(DISTANCE_ATTENUATION_FACTOR_PATH, defaults.getDistanceAttenuationFactor()), 1.0d, 0.0d),
				ConfigurationManager.ensureMin(section.getDouble(EXPLOSION_RADIUS_FACTOR_PATH, defaults.getExplosionRadiusFactor()), 0.0d),
				ConfigurationManager.ensureMin(section.getDouble(UNDERWATER_DAMAGE_FACTOR_PATH, defaults.getUnderwaterDamageFactor()), 0.0d),
				section.getBoolean(FANCY_UNDERWATER_DETECTION_PATH, defaults.isFancyUnderwaterDetection()),
				(soundConfigurationSection != null) ? SoundConfiguration.fromConfigurationSection(soundConfigurationSection) : SoundConfiguration.byDefault()
			);
	}

	private EntityMaterialConfiguration(double damage, double dropChance, double distanceAttenuationFactor,
			double explosionRadiusFactor, double underwaterDamageFactor, boolean fancyUnderwaterDetection,
			SoundConfiguration soundConfiguration) {
		super();
		this.damage = damage;
		this.dropChance = dropChance;
		this.distanceAttenuationFactor = distanceAttenuationFactor;
		this.explosionRadiusFactor = explosionRadiusFactor;
		this.underwaterDamageFactor = underwaterDamageFactor;
		this.fancyUnderwaterDetection = fancyUnderwaterDetection;
		this.soundConfiguration = soundConfiguration;
	}

	public double getDamage() {
		return damage;
	}

	public double getDropChance() {
		return dropChance;
	}

	public boolean shouldBeDropped() {
		return Math.random() <= getDropChance();
	}

	public double getDistanceAttenuationFactor() {
		return distanceAttenuationFactor;
	}

	public double getExplosionRadiusFactor() {
		return explosionRadiusFactor;
	}
	
	public double getUnderwaterDamageFactor() {
		return underwaterDamageFactor;
	}

	public boolean isUnderwaterAffected() {
		return getUnderwaterDamageFactor() != 1.0;
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
				+ soundConfiguration + "]";
	}
}
