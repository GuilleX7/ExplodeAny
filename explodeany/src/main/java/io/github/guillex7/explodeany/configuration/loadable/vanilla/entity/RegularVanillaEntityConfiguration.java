package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class RegularVanillaEntityConfiguration extends LoadableConfigurationSection<String> {
    public static String getConfigurationId() {
        return "VanillaEntity";
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
    public String getEntityName(String entity) {
        return entity;
    }

    @Override
    public String getEntityFromName(String name) {
        ExplodingVanillaEntity explodingVanillaEntity = ExplodingVanillaEntity.fromEntityTypeName(name);
        return explodingVanillaEntity != null ? explodingVanillaEntity.getName() : null;
    }
}
