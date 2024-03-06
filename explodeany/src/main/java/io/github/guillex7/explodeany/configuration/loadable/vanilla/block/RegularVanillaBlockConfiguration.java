package io.github.guillex7.explodeany.configuration.loadable.vanilla.block;

import io.github.guillex7.explodeany.compat.common.data.ExplodingVanillaMaterial;

public class RegularVanillaBlockConfiguration extends BaseVanillaBlockConfiguration {
    public static String getConfigurationId() {
        return "VanillaBlock";
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public String getSectionPath() {
        return RegularVanillaBlockConfiguration.getConfigurationId();
    }

    @Override
    public boolean isEntityValid(String entity) {
        return super.isEntityValid(entity) && ExplodingVanillaMaterial.isMaterialNameValid(entity);
    }
}
