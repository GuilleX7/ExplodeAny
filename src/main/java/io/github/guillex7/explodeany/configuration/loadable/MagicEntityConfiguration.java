package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.elmakers.mine.bukkit.api.magic.MagicAPI;

public class MagicEntityConfiguration extends BaseVanillaEntityConfiguration {
    @Override
    public boolean shouldBeLoaded() {
        Plugin externalPlugin = Bukkit.getPluginManager().getPlugin("Magic");
        return externalPlugin != null && externalPlugin instanceof MagicAPI;
    }

    @Override
    public String getSectionPath() {
        return "MagicEntity";
    }
}
