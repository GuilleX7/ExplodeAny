package io.github.guillex7.explodeany.compat.bukkit.v1_21_paper.api;

import java.util.Locale;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
public class CSoundUtils extends io.github.guillex7.explodeany.compat.bukkit.v1_20.api.CSoundUtils {
    @Override
    public Sound getSound(final String name) {
        if (name == null) {
            return null;
        }

        final Registry<Sound> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.SOUND_EVENT);
        if (registry == null) {
            return null;
        }

        final NamespacedKey key = NamespacedKey.fromString(
                name.toLowerCase(Locale.ROOT).replace('_', '.'));
        return key == null ? null : registry.get(key);
    }
}
