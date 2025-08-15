package io.github.guillex7.explodeany.configuration.section.specific;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.util.MathUtils;

public class TNTSpecificEntityConfiguration implements SpecificEntityConfiguration {
    private final double minimumDistanceToDamageBlocksUnderwater;
    private final double minimumSquaredDistanceToDamageBlocksUnderwater;
    private final boolean doSnapToBlockGridOnPriming;
    private final boolean doDisableExplosionChaining;

    public static final String minimumDistanceToDamageBlocksUnderwaterPath = "MinimumDistanceTravelledToDamageBlocksUnderwater";
    public static final String doSnapToBlockGridOnPrimingPath = "SnapToBlockGridOnPriming";
    public static final String doDisableExplosionChainingPath = "DisableExplosionChaining";

    public static TNTSpecificEntityConfiguration byDefault() {
        return new TNTSpecificEntityConfiguration(0.0, false, false);
    }

    public static TNTSpecificEntityConfiguration fromConfigurationSection(ConfigurationSection section) {
        TNTSpecificEntityConfiguration defaults = TNTSpecificEntityConfiguration.byDefault();

        return new TNTSpecificEntityConfiguration(
                MathUtils.ensureMin(section.getDouble(minimumDistanceToDamageBlocksUnderwaterPath,
                        defaults.getMinimumDistanceToDamageBlocksUnderwater()), 0.0),
                section.getBoolean(doSnapToBlockGridOnPrimingPath, defaults.doSnapToBlockGridOnPriming()),
                section.getBoolean(doDisableExplosionChainingPath, defaults.doDisableExplosionChaining()));
    }

    private TNTSpecificEntityConfiguration(double minimumDistanceToDamageBlocksUnderwater,
            boolean doSnapToBlockGridOnPriming, boolean doDisableExplosionChaining) {
        this.minimumDistanceToDamageBlocksUnderwater = minimumDistanceToDamageBlocksUnderwater;
        this.minimumSquaredDistanceToDamageBlocksUnderwater = minimumDistanceToDamageBlocksUnderwater
                * minimumDistanceToDamageBlocksUnderwater;
        this.doSnapToBlockGridOnPriming = doSnapToBlockGridOnPriming;
        this.doDisableExplosionChaining = doDisableExplosionChaining;
    }

    public double getMinimumDistanceToDamageBlocksUnderwater() {
        return minimumDistanceToDamageBlocksUnderwater;
    }

    public double getMinimumSquaredDistanceToDamageBlocksUnderwater() {
        return minimumSquaredDistanceToDamageBlocksUnderwater;
    }

    public boolean doSnapToBlockGridOnPriming() {
        return doSnapToBlockGridOnPriming;
    }

    public boolean doDisableExplosionChaining() {
        return doDisableExplosionChaining;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("&fMin. distance travelled to damage blocks underwater: ");
        if (this.getMinimumDistanceToDamageBlocksUnderwater() > 0.0) {
            builder.append(String.format("%.2f", this.getMinimumDistanceToDamageBlocksUnderwater())).append("\n");
        } else {
            builder.append("none").append("\n");
        }
        builder.append("&fSnap to block grid on priming: ").append(this.doSnapToBlockGridOnPriming()).append("\n");
        builder.append("&fDisable explosion chaining: ").append(this.doDisableExplosionChaining())
                .append("\n");
        return builder.toString();
    }
}
