package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.elmakers.mine.bukkit.api.magic.MagicAPI;

import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class MagicVanillaEntityConfiguration extends LoadableConfigurationSection<String> {
    private static final Version MINIMUM_SUPPORTED_BUKKIT_VERSION = new Version(1, 16);

    public static String getConfigurationId() {
        return "MagicEntity";
    }

    @Override
    public boolean shouldBeLoaded() {
        Plugin magicPlugin = Bukkit.getPluginManager().getPlugin("Magic");
        return magicPlugin instanceof MagicAPI
                && CompatibilityManager.getInstance().getApi().getMinimumSupportedBukkitVersion()
                        .isEqualOrAfter(MINIMUM_SUPPORTED_BUKKIT_VERSION);
    }

    @Override
    public String getSectionPath() {
        return MagicVanillaEntityConfiguration.getConfigurationId();
    }

    @Override
    public String getEntityName(String entity) {
        return entity;
    }

    @Override
    public String getEntityFromName(String name) {
        ExplodingVanillaEntity explodingVanillaEntity = ExplodingVanillaEntity.fromEntityTypeName(name);
        return explodingVanillaEntity != null ? explodingVanillaEntity.getName() : null;
    }
}
