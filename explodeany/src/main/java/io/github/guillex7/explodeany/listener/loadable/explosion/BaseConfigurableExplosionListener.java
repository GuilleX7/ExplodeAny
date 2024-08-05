package io.github.guillex7.explodeany.listener.loadable.explosion;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.listener.loadable.LoadableListener;

public abstract class BaseConfigurableExplosionListener extends LoadableListener {
    protected LoadableConfigurationSection<?> configuration;

    protected BaseConfigurableExplosionListener() {
        this.configuration = this.getConfiguration();
    }

    @Override
    public boolean shouldBeLoaded() {
        return ConfigurationManager.getInstance().isConfigurationSectionLoaded(this.configuration.getSectionPath());
    }

    protected abstract LoadableConfigurationSection<?> getConfiguration();
}
