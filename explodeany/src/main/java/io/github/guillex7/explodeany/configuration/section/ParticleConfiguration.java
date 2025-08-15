package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.api.IParticle;
import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;
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

    private final IParticle particle;
    private final double deltaX;
    private final double deltaY;
    private final double deltaZ;
    private final int amount;
    private final double speed;
    private final boolean force;

    public static ParticleConfiguration byDefault() {
        return new ParticleConfiguration(null, 1.0d, 1.0d, 1.0d, 1000, 1.0d, false);
    }

    public static ParticleConfiguration fromConfigurationSection(ConfigurationSection section) {
        ParticleConfiguration defaults = ParticleConfiguration.byDefault();

        String name = section.getString(NAME_PATH, "").toUpperCase();
        int red = MathUtils.ensureRange(section.getInt(RED_PATH, 0), 255, 0);
        int green = MathUtils.ensureRange(section.getInt(GREEN_PATH, 0), 255, 0);
        int blue = MathUtils.ensureRange(section.getInt(BLUE_PATH, 0), 255, 0);
        double size = MathUtils.ensureMin(section.getDouble(SIZE_PATH, 1), 0);

        String materialString = section.getString(MATERIAL_PATH, "").toUpperCase();
        Material material = Material.getMaterial(materialString);
        if (material == null && !materialString.equals("")) {
            ExplodeAny.getInstance().getLogger()
                    .warning(String.format("Invalid material '%s' in configuration section '%s'. Using default value.",
                            section.getString(MATERIAL_PATH), section.getCurrentPath()));
        }

        IParticle particle = CompatibilityManager.getInstance().getApi().getParticleUtils()
                .createParticle(new EanyParticleData(name, red, green, blue, size, material));

        return new ParticleConfiguration(particle, section.getDouble(DELTA_X_PATH, defaults.getDeltaX()),
                section.getDouble(DELTA_Y_PATH, defaults.getDeltaY()),
                section.getDouble(DELTA_Z_PATH, defaults.getDeltaZ()),
                MathUtils.ensureMin(section.getInt(AMOUNT_PATH, defaults.getAmount()), 0),
                MathUtils.ensureMin(section.getDouble(SPEED_PATH, defaults.getSpeed()), 0),
                section.getBoolean(FORCE_PATH, defaults.isForce()));
    }

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

    public void spawnAt(Location location) {
        if (this.isValid()) {
            this.particle.spawn(location.getWorld(), location.getX(), location.getY(), location.getZ(), this.amount,
                    this.deltaX, this.deltaY, this.deltaZ, this.speed, this.force);
        }
    }

    public boolean isValid() {
        return this.particle != null && this.particle.isValid();
    }

    public IParticle getParticle() {
        return particle;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public int getAmount() {
        return amount;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isForce() {
        return force;
    }

    @Override
    public String toString() {
        if (!this.isValid()) {
            return "(None)";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(this.getParticle().toString()).append("\n");
        builder.append("dX: ").append(String.format("%.2f", this.getDeltaX()))
                .append(" dY: ").append(String.format("%.2f", this.getDeltaY()))
                .append(" dZ: ").append(String.format("%.2f", this.getDeltaZ())).append("\n");
        builder.append("Amount: ").append(this.getAmount()).append("\n");
        builder.append("Speed: ").append(String.format("%.2f", this.getSpeed())).append("\n");
        builder.append("Force: ").append(this.isForce());
        return builder.toString();
    }

    public ParticleConfiguration clone() {
        return new ParticleConfiguration(this.particle, this.deltaX, this.deltaY, this.deltaZ, this.amount, this.speed,
                this.force);
    }
}
