package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;
import io.github.guillex7.explodeany.compat.common.data.IParticle;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class ParticleConfiguration {
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

    public static ParticleConfiguration fromConfigurationSection(final ConfigurationSection section) {
        final ParticleConfiguration defaults = ParticleConfiguration.byDefault();

        final String name = section.getString(ParticleConfiguration.NAME_PATH, "").toUpperCase();
        final int red = MathUtils.ensureRange(section.getInt(ParticleConfiguration.RED_PATH, 0), 255, 0);
        final int green = MathUtils.ensureRange(section.getInt(ParticleConfiguration.GREEN_PATH, 0), 255, 0);
        final int blue = MathUtils.ensureRange(section.getInt(ParticleConfiguration.BLUE_PATH, 0), 255, 0);
        final double size = MathUtils.ensureMin(section.getDouble(ParticleConfiguration.SIZE_PATH, 1), 0);

        final String materialName = section.getString(ParticleConfiguration.MATERIAL_PATH, "").toUpperCase();
        final Material material = Material.getMaterial(materialName);
        if (material == null && !"".equals(materialName)) {
            ExplodeAny.getInstance().getLogger()
                    .warning(String.format("Invalid material '%s' in configuration section '%s'. Using default value.",
                            section.getString(ParticleConfiguration.MATERIAL_PATH), section.getCurrentPath()));
        }

        final IParticle particle = CompatibilityManager.getInstance().getApi().getParticleUtils()
                .createParticle(new EanyParticleData(name, red, green, blue, size, material));

        return new ParticleConfiguration(particle,
                section.getDouble(ParticleConfiguration.DELTA_X_PATH, defaults.getDeltaX()),
                section.getDouble(ParticleConfiguration.DELTA_Y_PATH, defaults.getDeltaY()),
                section.getDouble(ParticleConfiguration.DELTA_Z_PATH, defaults.getDeltaZ()),
                MathUtils.ensureMin(section.getInt(ParticleConfiguration.AMOUNT_PATH, defaults.getAmount()), 0),
                MathUtils.ensureMin(section.getDouble(ParticleConfiguration.SPEED_PATH, defaults.getSpeed()), 0),
                section.getBoolean(ParticleConfiguration.FORCE_PATH, defaults.isForce()));
    }

    public ParticleConfiguration(final IParticle particle, final double deltaX, final double deltaY,
            final double deltaZ, final int amount,
            final double speed, final boolean force) {
        this.particle = particle;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.amount = amount;
        this.speed = speed;
        this.force = force;
    }

    public void spawnAt(final Location location) {
        if (this.isValid()) {
            this.particle.spawn(location.getWorld(), location.getX(), location.getY(), location.getZ(), this.amount,
                    this.deltaX, this.deltaY, this.deltaZ, this.speed, this.force);
        }
    }

    public boolean isValid() {
        return this.particle != null && this.particle.isValid();
    }

    public IParticle getParticle() {
        return this.particle;
    }

    public double getDeltaX() {
        return this.deltaX;
    }

    public double getDeltaY() {
        return this.deltaY;
    }

    public double getDeltaZ() {
        return this.deltaZ;
    }

    public int getAmount() {
        return this.amount;
    }

    public double getSpeed() {
        return this.speed;
    }

    public boolean isForce() {
        return this.force;
    }

    @Override
    public String toString() {
        if (!this.isValid()) {
            return "(None)";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append(this.getParticle().toString()).append("\n");
        builder.append("dX: ").append(String.format("%.2f", this.getDeltaX()))
                .append(" dY: ").append(String.format("%.2f", this.getDeltaY()))
                .append(" dZ: ").append(String.format("%.2f", this.getDeltaZ())).append("\n");
        builder.append("Amount: ").append(this.getAmount()).append("\n");
        builder.append("Speed: ").append(String.format("%.2f", this.getSpeed())).append("\n");
        builder.append("Force: ").append(this.isForce());
        return builder.toString();
    }

    @Override
    public ParticleConfiguration clone() {
        return new ParticleConfiguration(this.particle, this.deltaX, this.deltaY, this.deltaZ, this.amount, this.speed,
                this.force);
    }
}
