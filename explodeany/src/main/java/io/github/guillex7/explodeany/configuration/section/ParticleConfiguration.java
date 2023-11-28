package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.compat.common.api.IParticle;
import io.github.guillex7.explodeany.compat.common.api.IParticle.ParticleData;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class ParticleConfiguration {
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
    private static final String MATERIAL_PATH = "Material";

    private IParticle particle;
    private Double deltaX;
    private Double deltaY;
    private Double deltaZ;
    private Integer amount;
    private Double speed;
    private Boolean force;

    public ParticleConfiguration(IParticle particle, Double deltaX, Double deltaY, Double deltaZ, Integer amount,
            Double speed, Boolean force) {
        this.particle = particle;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.amount = amount;
        this.speed = speed;
        this.force = force;
    }

    public static ParticleConfiguration of(IParticle particle, Double deltaX, Double deltaY, Double deltaZ,
            Integer amount, Double speed, Boolean force) {
        return new ParticleConfiguration(particle, deltaX, deltaY, deltaZ, amount, speed, force);
    }

    public static ParticleConfiguration byDefault() {
        return new ParticleConfiguration(null, 1.0d, 1.0d, 1.0d, 1000, 1.0d, false);
    }

    public static ParticleConfiguration fromConfigurationSection(ConfigurationSection section) {
        ParticleConfiguration defaults = ParticleConfiguration.byDefault();

        String name = section.getString(NAME_PATH).toUpperCase();
        Integer red = MathUtils.ensureRange(section.getInt(RED_PATH, 0), 255, 0);
        Integer green = MathUtils.ensureRange(section.getInt(GREEN_PATH, 0), 255, 0);
        Integer blue = MathUtils.ensureRange(section.getInt(BLUE_PATH, 0), 255, 0);
        float size = (float) MathUtils.ensureMin(section.getDouble(SIZE_PATH, 1), 0);
        Material material;

        try {
            material = Material.valueOf(section.getString(MATERIAL_PATH).toUpperCase());
        } catch (Exception e) {
            material = null;
        }

        IParticle particle = CompatibilityManager.getInstance().getApi().getParticleUtils()
                .createParticle(new ParticleData(name, red, green, blue, size, material));

        return ParticleConfiguration.of(particle, section.getDouble(DELTA_X_PATH, defaults.getDeltaX()),
                section.getDouble(DELTA_Y_PATH, defaults.getDeltaY()),
                section.getDouble(DELTA_Z_PATH, defaults.getDeltaZ()),
                MathUtils.ensureMin(section.getInt(AMOUNT_PATH, defaults.getAmount()), 0),
                MathUtils.ensureMin(section.getDouble(SPEED_PATH, defaults.getSpeed()), 0),
                section.getBoolean(FORCE_PATH, defaults.isForce()));
    }

    public void spawnAt(Location location) {
        if (this.particle != null) {
            this.particle.spawn(location.getWorld(), location.getX(), location.getY(), location.getZ(), this.amount,
                    this.deltaX, this.deltaY, this.deltaZ, this.speed, this.force);
        }
    }

    public IParticle getParticle() {
        return particle;
    }

    public void setParticle(IParticle particle) {
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

    public void setIsForce(Boolean force) {
        this.force = force;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((particle == null) ? 0 : particle.hashCode());
        result = prime * result + ((deltaX == null) ? 0 : deltaX.hashCode());
        result = prime * result + ((deltaY == null) ? 0 : deltaY.hashCode());
        result = prime * result + ((deltaZ == null) ? 0 : deltaZ.hashCode());
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((speed == null) ? 0 : speed.hashCode());
        result = prime * result + ((force == null) ? 0 : force.hashCode());
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
        if (particle == null) {
            if (other.particle != null)
                return false;
        } else if (!particle.equals(other.particle))
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
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (speed == null) {
            if (other.speed != null)
                return false;
        } else if (!speed.equals(other.speed))
            return false;
        if (force == null) {
            if (other.force != null)
                return false;
        } else if (!force.equals(other.force))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ParticleConfiguration [particle=" + particle + ", deltaX=" + deltaX + ", deltaY=" + deltaY + ", deltaZ="
                + deltaZ + ", amount=" + amount + ", speed=" + speed + ", force=" + force + "]";
    }
}
