package io.github.guillex7.explodeany.configuration.loadable.vanilla;

import io.github.guillex7.explodeany.compat.common.data.ExplodingVanillaEntity;

public class ExplodingVanillaEntityConfiguration extends BaseVanillaEntityConfiguration {
    public static String getConfigurationId() {
        return "VanillaEntity";
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public String getSectionPath() {
        return ExplodingVanillaEntityConfiguration.getConfigurationId();
    }

    @Override
    public boolean isEntityValid(String entity) {
        return super.isEntityValid(entity) && ExplodingVanillaEntity.isEntityNameValid(entity);
    }
}
