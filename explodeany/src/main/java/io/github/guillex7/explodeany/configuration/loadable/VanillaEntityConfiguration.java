package io.github.guillex7.explodeany.configuration.loadable;

public class VanillaEntityConfiguration extends BaseVanillaEntityConfiguration {
    public static String getConfigurationId() {
        return "VanillaEntity";
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public String getSectionPath() {
        return VanillaEntityConfiguration.getConfigurationId();
    }
}
