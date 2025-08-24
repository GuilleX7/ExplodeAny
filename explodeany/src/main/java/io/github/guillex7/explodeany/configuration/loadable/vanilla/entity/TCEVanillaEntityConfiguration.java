package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class TCEVanillaEntityConfiguration extends LoadableConfigurationSection<ExplodingVanillaEntity> {
    public static String getConfigurationId() {
        return "ThrowableCreeperEggs";
    }

    @Override
    public String getHumanReadableName() {
        return "Throwable Creeper Eggs";
    }

    @Override
    public boolean shouldBeLoaded() {
        final Plugin tcePlugin = Bukkit.getPluginManager().getPlugin("ThrowableCreeperEggs");
        return tcePlugin != null && tcePlugin.isEnabled()
                && tcePlugin instanceof dev.pixelmania.throwablecreepereggs.Core;
    }

    @Override
    public String getSectionPath() {
        return TCEVanillaEntityConfiguration.getConfigurationId();
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
