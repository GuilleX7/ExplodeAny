package io.github.guillex7.explodeany.configuration.section;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.ExplodeAny;
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
        String soundString = section.getString(NAME_PATH, "").toUpperCase();
        try {
            sound = Sound.valueOf(soundString);
        } catch (Exception e) {
            if (!soundString.equals("")) {
                ExplodeAny.getInstance().getLogger()
                        .warning(String.format("Invalid sound '%s' in configuration section '%s'. Using default value.",
                                section.getString(NAME_PATH), section.getCurrentPath()));
            }
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
        if (!this.isValid()) {
            return "(None)";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Sound name: ").append(this.getSound().name()).append("\n");
        builder.append("Volume: ").append(String.format("%.2f", this.getVolume())).append("\n");
        builder.append("Pitch: ").append(String.format("%.2f", this.getPitch()));
        return builder.toString();
    }

    public SoundConfiguration clone() {
        return new SoundConfiguration(this.sound, this.volume, this.pitch);
    }
}
