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
    private double deltaX;
    private double deltaY;
    private double deltaZ;
    private int amount;
    private double speed;
    private boolean force;

    public ParticleConfiguration(IParticle particle, double deltaX, double deltaY, double deltaZ, int amount,
            double speed, boolean force) {
        this.particle = particle;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.amount = amount;
        this.speed = speed;
        this.force = force;
    }

    public static ParticleConfiguration of(IParticle particle, double deltaX, double deltaY, double deltaZ,
            int amount, double speed, boolean force) {
        return new ParticleConfiguration(particle, deltaX, deltaY, deltaZ, amount, speed, force);
    }

    public static ParticleConfiguration byDefault() {
        return new ParticleConfiguration(null, 1.0d, 1.0d, 1.0d, 1000, 1.0d, false);
    }

    public static ParticleConfiguration fromConfigurationSection(ConfigurationSection section) {
        ParticleConfiguration defaults = ParticleConfiguration.byDefault();

        String name = section.getString(NAME_PATH).toUpperCase();
        int red = MathUtils.ensureRange(section.getInt(RED_PATH, 0), 255, 0);
        int green = MathUtils.ensureRange(section.getInt(GREEN_PATH, 0), 255, 0);
        int blue = MathUtils.ensureRange(section.getInt(BLUE_PATH, 0), 255, 0);
        double size = MathUtils.ensureMin(section.getDouble(SIZE_PATH, 1), 0);
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

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public void setDeltaZ(double deltaZ) {
        this.deltaZ = deltaZ;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isForce() {
        return force;
    }

    public void setIsForce(boolean force) {
        this.force = force;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((particle == null) ? 0 : particle.hashCode());
        long temp;
        temp = Double.doubleToLongBits(deltaX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(deltaY);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(deltaZ);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + amount;
        temp = Double.doubleToLongBits(speed);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (force ? 1231 : 1237);
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
        if (Double.doubleToLongBits(deltaX) != Double.doubleToLongBits(other.deltaX))
            return false;
        if (Double.doubleToLongBits(deltaY) != Double.doubleToLongBits(other.deltaY))
            return false;
        if (Double.doubleToLongBits(deltaZ) != Double.doubleToLongBits(other.deltaZ))
            return false;
        if (amount != other.amount)
            return false;
        if (Double.doubleToLongBits(speed) != Double.doubleToLongBits(other.speed))
            return false;
        if (force != other.force)
            return false;
        return true;
    }
}
