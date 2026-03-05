package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.elmakers.mine.bukkit.api.magic.MagicAPI;

import io.github.guillex7.explodeany.compat.common.Environment;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class MagicVanillaEntityConfiguration extends LoadableConfigurationSection<ExplodingVanillaEntity> {
    private final Version minimumSupportedBukkitVersion = new Version(1, 16);

    public static String getConfigurationId() {
        return "MagicEntity";
    }

    @Override
    public String getHumanReadableName() {
        return "Magic entities";
    }

    @Override
    public boolean shouldBeLoaded() {
        final Plugin magicPlugin = Bukkit.getPluginManager().getPlugin("Magic");
        return magicPlugin != null && magicPlugin.isEnabled() && magicPlugin instanceof MagicAPI
                && Environment.getBukkitVersion()
                        .isEqualOrAfter(this.minimumSupportedBukkitVersion);
    }

    @Override
    public String getSectionPath() {
        return MagicVanillaEntityConfiguration.getConfigurationId();
    }

    @Override
    public String getEntityName(final ExplodingVanillaEntity entity) {
        return entity.getName();
    }

    @Override
    public ExplodingVanillaEntity getEntityFromName(final String name) {
        return ExplodingVanillaEntity.fromEntityTypeName(name);
    }

    @Override
    public List<ExplodingVanillaEntity> getEntitiesFromPattern(final Pattern pattern, final String name) {
        return Arrays.stream(ExplodingVanillaEntity.values())
                .filter(entity -> pattern.matcher(entity.getName()).matches())
                .collect(Collectors.toList());
    }
}
