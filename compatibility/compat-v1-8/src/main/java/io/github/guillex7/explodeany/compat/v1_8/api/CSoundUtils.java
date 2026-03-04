package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.Sound;

import io.github.guillex7.explodeany.compat.common.api.ISoundUtils;

public class CSoundUtils implements ISoundUtils {
    @Override
    public Sound getSound(final String name) {
        try {
            return Sound.valueOf(name);
        } catch (final Exception e) {
            return null;
        }
    }
}
