package io.github.guillex7.explodeany.configuration.section;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class ParticleConfiguration {
	private static final Set<Particle> particlesWithData = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(Particle.REDSTONE)));

	public static final String ROOT_PATH = "Particles";
	private static final String NAME_PATH = "Name";
	private static final String DELTA_X_PATH = "DeltaX";
	private static final String DELTA_Y_PATH = "DeltaY";
	private static final String DELTA_Z_PATH = "DeltaZ";
	private static final String AMOUNT_PATH = "Amount";
	private static final String SPEED_PATH = "Speed";
	private static final String FORCE_PATH = "Force";
	private static final String RED_PATH = "Red";
	private static final String BLUE_PATH = "Blue";
	private static final String GREEN_PATH = "Green";
	private static final String SIZE_PATH = "Size";

	private Particle particle;
	private Double deltaX;
	private Double deltaY;
	private Double deltaZ;
	private Integer amount;
	private Double speed;
	private Boolean force;
	private DustOptions options;

	public static ParticleConfiguration of(Particle particle, Double deltaX, Double deltaY, Double deltaZ,
			Integer amount, Double speed, Boolean force, DustOptions options) {
		return new ParticleConfiguration(particle, deltaX, deltaY, deltaZ, amount, speed, force, options);
	}

	public static ParticleConfiguration byDefault() {
		return new ParticleConfiguration(null, 1.0d, 1.0d, 1.0d, 1000, 1.0d, false, new DustOptions(Color.BLACK, 1.0f));
	}

	public static ParticleConfiguration fromConfigurationSection(ConfigurationSection section) {
		ParticleConfiguration defaults = ParticleConfiguration.byDefault();
		
		Particle particle;
		try {
			particle = Particle.valueOf(section.getString(NAME_PATH).toUpperCase(Locale.ROOT));
		} catch (Exception e) {
			particle = null;
		}
		
		DustOptions options;
		if (particlesWithData.contains(particle)) {
			options = new DustOptions(
				Color.fromRGB(
					ConfigurationManager.ensureRange(section.getInt(RED_PATH, defaults.getOptions().getColor().getRed()), 255, 0),
					ConfigurationManager.ensureRange(section.getInt(GREEN_PATH, defaults.getOptions().getColor().getGreen()), 255, 0),
					ConfigurationManager.ensureRange(section.getInt(BLUE_PATH, defaults.getOptions().getColor().getBlue()), 255, 0)
				),
				(float) ConfigurationManager.ensureMin(section.getDouble(SIZE_PATH, defaults.getOptions().getSize()), 0)
			);
		} else {
			options = null;
		}
		
		return ParticleConfiguration.of(
				particle,
				section.getDouble(DELTA_X_PATH, defaults.getDeltaX()),
				section.getDouble(DELTA_Y_PATH, defaults.getDeltaY()),
				section.getDouble(DELTA_Z_PATH, defaults.getDeltaZ()),
				ConfigurationManager.ensureMin(section.getInt(AMOUNT_PATH, defaults.getAmount()), 0),
				ConfigurationManager.ensureMin(section.getDouble(SPEED_PATH, defaults.getSpeed()), 0),
				section.getBoolean(FORCE_PATH, defaults.isForce()),
				options
			);
	}

	public ParticleConfiguration(Particle particle, Double deltaX, Double deltaY, Double deltaZ, Integer amount,
			Double speed, Boolean force, DustOptions options) {
		super();
		this.particle = particle;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.amount = amount;
		this.speed = speed;
		this.force = force;
		this.options = options;
	}

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	public Double getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(Double deltaX) {
		this.deltaX = deltaX;
	}

	public Double getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(Double deltaY) {
		this.deltaY = deltaY;
	}

	public Double getDeltaZ() {
		return deltaZ;
	}

	public void setDeltaZ(Double deltaZ) {
		this.deltaZ = deltaZ;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Boolean isForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}

	public DustOptions getOptions() {
		return options;
	}

	public void setOptions(DustOptions options) {
		this.options = options;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((deltaX == null) ? 0 : deltaX.hashCode());
		result = prime * result + ((deltaY == null) ? 0 : deltaY.hashCode());
		result = prime * result + ((deltaZ == null) ? 0 : deltaZ.hashCode());
		result = prime * result + ((force == null) ? 0 : force.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((particle == null) ? 0 : particle.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
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
		ParticleConfiguration other = (ParticleConfiguration) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (deltaX == null) {
			if (other.deltaX != null)
				return false;
		} else if (!deltaX.equals(other.deltaX))
			return false;
		if (deltaY == null) {
			if (other.deltaY != null)
				return false;
		} else if (!deltaY.equals(other.deltaY))
			return false;
		if (deltaZ == null) {
			if (other.deltaZ != null)
				return false;
		} else if (!deltaZ.equals(other.deltaZ))
			return false;
		if (force == null) {
			if (other.force != null)
				return false;
		} else if (!force.equals(other.force))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (particle != other.particle)
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParticleConfiguration [particle=" + particle + ", deltaX=" + deltaX + ", deltaY=" + deltaY + ", deltaZ="
				+ deltaZ + ", amount=" + amount + ", speed=" + speed + ", force=" + force + ", options=" + options
				+ "]";
	}
}
