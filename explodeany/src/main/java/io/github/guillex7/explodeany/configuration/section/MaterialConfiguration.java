package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.data.Duration;

public class MaterialConfiguration {
    private static final String TIME_TO_FULL_REGENERATION_PATH = "TimeToFullRegeneration";
    private static final String DELAY_BEFORE_START_REGENERATING_PATH = "DelayBeforeStartRegenerating";

    private final Duration timeToFullRegeneration;
    private final Duration delayBeforeStartRegenerating;

    // Hint: derived, but useful for performance
    private final double durabilityRegenerationPerMillisecond;

    public static MaterialConfiguration byDefault() {
        return new MaterialConfiguration(Duration.zero(), Duration.zero());
    }

    public static MaterialConfiguration fromConfigurationSection(ConfigurationSection section) {
        final Duration timeToFullRegeneration = Duration.parse(section.getString(TIME_TO_FULL_REGENERATION_PATH));
        final Duration delayBeforeStartRegenerating = Duration
                .parse(section.getString(DELAY_BEFORE_START_REGENERATING_PATH));

        return new MaterialConfiguration(
                timeToFullRegeneration,
                delayBeforeStartRegenerating);
    }

    private MaterialConfiguration(Duration timeToFullRegeneration, Duration delayBeforeStartRegenerating) {
        this.timeToFullRegeneration = timeToFullRegeneration;
        this.delayBeforeStartRegenerating = delayBeforeStartRegenerating;
        this.durabilityRegenerationPerMillisecond = !timeToFullRegeneration.isZero()
                ? ConfigurationManager.getInstance().getGlobalBlockDurability()
                / (double) timeToFullRegeneration.asMilliseconds()
                : 0.0d;
    }

    public boolean doRegenerateDurability() {
        return this.durabilityRegenerationPerMillisecond > 0.0d;
    }

    public Duration getTimeToFullRegeneration() {
        return timeToFullRegeneration;
    }

    public Duration getDelayBeforeRegeneration() {
        return delayBeforeStartRegenerating;
    }

    public double getDurabilityRegenerationPerMillisecond() {
        return durabilityRegenerationPerMillisecond;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("&7<Regeneration>\n");

        if (!timeToFullRegeneration.isZero()) {
            builder.append(String.format(
                    "&fTime to full regeneration: %s\n"
                    + "&fDelay before start regenerating: %s\n",
                    timeToFullRegeneration.toString(),
                    delayBeforeStartRegenerating.toString()));
        } else {
            builder.append("&f(Disabled)\n");
        }

        return builder.toString();
    }
}
