package io.github.guillex7.explodeany.compat.bukkit.v1_8.api;

import org.bukkit.Sound;

import io.github.guillex7.explodeany.compat.common.bukkit.api.ISoundUtils;

public class CSoundUtils implements ISoundUtils {
    @Override
    public Sound getSound(final String name) {
        for (final Sound sound : Sound.values()) {
            if (sound.name().equals(name)) {
                return sound;
            }
        }
        return null;
    }
}
