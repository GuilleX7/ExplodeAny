package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

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
        Plugin tcePlugin = Bukkit.getPluginManager().getPlugin("ThrowableCreeperEggs");
        return tcePlugin != null && tcePlugin.isEnabled()
                && tcePlugin instanceof dev.pixelmania.throwablecreepereggs.Core;
    }

    @Override
    public String getSectionPath() {
        return TCEVanillaEntityConfiguration.getConfigurationId();
    }

    @Override
    public String getEntityName(ExplodingVanillaEntity entity) {
        return entity.getName();
    }

    @Override
    public ExplodingVanillaEntity getEntityFromName(String name) {
        return ExplodingVanillaEntity.fromEntityTypeName(name);
    }
}
