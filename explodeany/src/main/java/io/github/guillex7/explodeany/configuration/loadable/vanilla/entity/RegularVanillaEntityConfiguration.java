package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class RegularVanillaEntityConfiguration extends LoadableConfigurationSection<ExplodingVanillaEntity> {
    public static String getConfigurationId() {
        return "VanillaEntity";
    }

    @Override
    public String getHumanReadableName() {
        return "Vanilla entities and blocks";
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public String getSectionPath() {
        return RegularVanillaEntityConfiguration.getConfigurationId();
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
