package io.github.guillex7.explodeany.configuration.loadable.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.elmakers.mine.bukkit.api.magic.MagicAPI;

import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.compat.common.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;

public class MagicVanillaEntityConfiguration extends BaseVanillaEntityConfiguration {
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
    public boolean isEntityValid(String entity) {
        return super.isEntityValid(entity) && ExplodingVanillaEntity.isEntityNameValid(entity);
    }
}
