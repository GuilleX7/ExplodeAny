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

    private Sound sound;
    private double volume;
    private double pitch;

    private SoundConfiguration(Sound sound, double volume, double pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static SoundConfiguration of(Sound sound, double volume, double pitch) {
        return new SoundConfiguration(sound, volume, pitch);
    }

    public static SoundConfiguration byDefault() {
        return SoundConfiguration.of(null, 1.0d, 1.0d);
    }

    public static SoundConfiguration fromConfigurationSection(ConfigurationSection section) {
        SoundConfiguration defaults = SoundConfiguration.byDefault();

        Sound sound;
        try {
            sound = Sound.valueOf(section.getString(NAME_PATH).toUpperCase());
        } catch (Exception e) {
            sound = null;
        }

        return SoundConfiguration.of(sound,
                MathUtils.ensureMin(section.getDouble(VOLUME_PATH, defaults.getVolume()), 0.0d),
                MathUtils.ensureRange(section.getDouble(PITCH_PATH, defaults.getPitch()), 2.0d, 0.5d));
    }

    public void playAt(Location location) {
        location.getWorld().playSound(location, this.sound,
                (float) this.volume,
                (float) this.pitch);
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sound == null) ? 0 : sound.hashCode());
        long temp;
        temp = Double.doubleToLongBits(volume);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pitch);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        SoundConfiguration other = (SoundConfiguration) obj;
        if (sound != other.sound)
            return false;
        if (Double.doubleToLongBits(volume) != Double.doubleToLongBits(other.volume))
            return false;
        if (Double.doubleToLongBits(pitch) != Double.doubleToLongBits(other.pitch))
            return false;
        return true;
    }
}
