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

    public static MaterialConfiguration fromConfigurationSection(final ConfigurationSection section) {
        final Duration timeToFullRegeneration = Duration
                .parse(section.getString(MaterialConfiguration.TIME_TO_FULL_REGENERATION_PATH));
        final Duration delayBeforeStartRegenerating = Duration
                .parse(section.getString(MaterialConfiguration.DELAY_BEFORE_START_REGENERATING_PATH));

        return new MaterialConfiguration(
                timeToFullRegeneration,
                delayBeforeStartRegenerating);
    }

    private MaterialConfiguration(final Duration timeToFullRegeneration, final Duration delayBeforeStartRegenerating) {
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
        return this.timeToFullRegeneration;
    }

    public Duration getDelayBeforeRegeneration() {
        return this.delayBeforeStartRegenerating;
    }

    public double getDurabilityRegenerationPerMillisecond() {
        return this.durabilityRegenerationPerMillisecond;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("&7<Regeneration>\n");

        if (!this.timeToFullRegeneration.isZero()) {
            builder.append(String.format(
                    "&fTime to full regeneration: %s\n"
                            + "&fDelay before start regenerating: %s\n",
                    this.timeToFullRegeneration.toString(),
                    this.delayBeforeStartRegenerating.toString()));
        } else {
            builder.append("&f(Disabled)\n");
        }

        return builder.toString();
    }
}
