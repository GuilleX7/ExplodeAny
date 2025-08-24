package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.util.MathUtils;

public class SoundConfiguration {
    private static final String NAME_PATH = "Name";
    private static final String VOLUME_PATH = "Volume";
    private static final String PITCH_PATH = "Pitch";

    private final Sound sound;
    private final String soundName;
    private final double volume;
    private final double pitch;

    public static SoundConfiguration byDefault() {
        return new SoundConfiguration(null, "", 1.0d, 1.0d);
    }

    public static SoundConfiguration fromConfigurationSection(final ConfigurationSection section) {
        final SoundConfiguration defaults = SoundConfiguration.byDefault();

        final String soundName = section.getString(SoundConfiguration.NAME_PATH, "").toUpperCase();
        final Sound sound = CompatibilityManager.getInstance().getApi().getSoundUtils().getSound(soundName);

        if (sound == null && !"".equals(soundName)) {
            ExplodeAny.getInstance().getLogger()
                    .warning(String.format("Invalid sound '%s' in configuration section '%s'. Using default value.",
                            section.getString(SoundConfiguration.NAME_PATH), section.getCurrentPath()));
        }

        return new SoundConfiguration(sound, soundName,
                MathUtils.ensureMin(section.getDouble(SoundConfiguration.VOLUME_PATH, defaults.getVolume()), 0.0d),
                MathUtils.ensureRange(section.getDouble(SoundConfiguration.PITCH_PATH, defaults.getPitch()), 2.0d,
                        0.5d));
    }

    public SoundConfiguration(final Sound sound, final String soundName, final double volume, final double pitch) {
        this.sound = sound;
        this.soundName = soundName;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void playAt(final Location location) {
        location.getWorld().playSound(location, this.sound,
                (float) this.volume,
                (float) this.pitch);
    }

    public boolean isValid() {
        return this.sound != null;
    }

    public Sound getSound() {
        return this.sound;
    }

    public String getSoundName() {
        return this.soundName;
    }

    public double getVolume() {
        return this.volume;
    }

    public double getPitch() {
        return this.pitch;
    }

    @Override
    public String toString() {
        if (!this.isValid()) {
            return "(None)";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("Sound name: ").append(this.getSoundName()).append("\n");
        builder.append("Volume: ").append(String.format("%.2f", this.getVolume())).append("\n");
        builder.append("Pitch: ").append(String.format("%.2f", this.getPitch()));
        return builder.toString();
    }

    @Override
    public SoundConfiguration clone() {
        return new SoundConfiguration(this.sound, this.soundName, this.volume, this.pitch);
    }
}
