package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class RegularVanillaEntityConfiguration extends BaseVanillaEntityConfiguration {
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
    public boolean isEntityValid(String entity) {
        return super.isEntityValid(entity) && ExplodingVanillaEntity.isEntityNameValid(entity);
    }
}
