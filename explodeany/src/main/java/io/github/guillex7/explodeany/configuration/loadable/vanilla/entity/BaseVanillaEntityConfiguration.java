package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;

public abstract class BaseVanillaEntityConfiguration extends LoadableConfigurationSection<String> {
    @Override
    public String getEntityName(String entity) {
        return entity;
    }

    @Override
    public String getEntityFromName(String name) {
        return name.toUpperCase();
    }

    @Override
    public boolean isEntityValid(String entity) {
        return entity != null;
    }
}
