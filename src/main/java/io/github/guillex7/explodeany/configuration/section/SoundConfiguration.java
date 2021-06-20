package io.github.guillex7.explodeany.configuration.section;

import java.util.Locale;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class SoundConfiguration {
	public static final String ROOT_PATH = "Sound";
	private static final String NAME_PATH = "Name";
	private static final String VOLUME_PATH = "Volume";
	private static final String PITCH_PATH = "Pitch";

	private Sound sound;
	private Double volume;
	private Double pitch;

	public static SoundConfiguration of(Sound sound, Double volume, Double pitch) {
		return new SoundConfiguration(sound, volume, pitch);
	}

	public static SoundConfiguration byDefault() {
		return SoundConfiguration.of(null, 1.0d, 1.0d);
	}

	public static SoundConfiguration fromConfigurationSection(ConfigurationSection section) {
		SoundConfiguration defaults = SoundConfiguration.byDefault();

		Sound sound;
		try {
			sound = Sound.valueOf(section.getString(NAME_PATH).toUpperCase(Locale.ROOT));
		} catch (Exception e) {
			sound = null;
		}

		return SoundConfiguration.of(sound,
				ConfigurationManager.ensureMin(section.getDouble(VOLUME_PATH, defaults.getVolume()), 0.0d),
				ConfigurationManager.ensureRange(section.getDouble(PITCH_PATH, defaults.getPitch()), 2.0d, 0.5d));
	}

	private SoundConfiguration(Sound sound, Double volume, Double pitch) {
		super();
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getPitch() {
		return pitch;
	}

	public void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pitch == null) ? 0 : pitch.hashCode());
		result = prime * result + ((sound == null) ? 0 : sound.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
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
		if (pitch == null) {
			if (other.pitch != null)
				return false;
		} else if (!pitch.equals(other.pitch))
			return false;
		if (sound != other.sound)
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SoundConfiguration [sound=" + sound + ", volume=" + volume + ", pitch=" + pitch + "]";
	}
}
