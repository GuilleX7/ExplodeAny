package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.util.MathUtils;

public class SoundConfiguration {
    public static final String ROOT_PATH = "Sound";
    private static final String NAME_PATH = "Name";
    private static final String VOLUME_PATH = "Volume";
    private static final String PITCH_PATH = "Pitch";

    private final Sound sound;
    private final double volume;
    private final double pitch;

    public static SoundConfiguration byDefault() {
        return new SoundConfiguration(null, 1.0d, 1.0d);
    }

    public static SoundConfiguration fromConfigurationSection(ConfigurationSection section) {
        SoundConfiguration defaults = SoundConfiguration.byDefault();

        Sound sound;
        try {
            sound = Sound.valueOf(section.getString(NAME_PATH, "").toUpperCase());
        } catch (Exception e) {
            sound = null;
        }

        return new SoundConfiguration(sound,
                MathUtils.ensureMin(section.getDouble(VOLUME_PATH, defaults.getVolume()), 0.0d),
                MathUtils.ensureRange(section.getDouble(PITCH_PATH, defaults.getPitch()), 2.0d, 0.5d));
    }

    public SoundConfiguration(Sound sound, double volume, double pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void playAt(Location location) {
        location.getWorld().playSound(location, this.sound,
                (float) this.volume,
                (float) this.pitch);
    }

    public boolean isValid() {
        return this.sound != null;
    }

    public Sound getSound() {
        return sound;
    }

    public double getVolume() {
        return volume;
    }

    public double getPitch() {
        return pitch;
    }

    @Override
    public String toString() {
        return this.isValid() ? String.format(
                "Sound name: %s\n" +
                        "Volume: %.2f\n" +
                        "Pitch: %.2f",
                this.getSound().name(), this.getVolume(), this.getPitch())
                : "(None)";
    }
}
