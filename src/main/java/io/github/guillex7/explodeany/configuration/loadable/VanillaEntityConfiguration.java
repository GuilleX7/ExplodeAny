package io.github.guillex7.explodeany.configuration.loadable;

public class VanillaEntityConfiguration extends BaseVanillaEntityConfiguration {
    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public String getSectionPath() {
        return "VanillaEntity";
    }
}
