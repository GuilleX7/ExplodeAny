package io.github.guillex7.explodeany.compat.v1_21_paper.api;

import org.bukkit.Registry;
import org.bukkit.Sound;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;

public class CSoundUtils extends io.github.guillex7.explodeany.compat.v1_20.api.CSoundUtils {
    @Override
    public Sound getSound(final String name) {
        try {
            final Registry<Sound> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.SOUND_EVENT);
            if (registry == null) {
                return null;
            }

            return registry.getOrThrow(TypedKey.create(
                    RegistryKey.SOUND_EVENT, Key.key(name.toLowerCase().replaceAll("_", "."))));
        } catch (final Exception e) {
            return null;
        }
    }
}
