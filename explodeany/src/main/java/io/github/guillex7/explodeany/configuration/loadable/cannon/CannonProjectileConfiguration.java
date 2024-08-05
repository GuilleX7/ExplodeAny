package io.github.guillex7.explodeany.configuration.loadable.cannon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import at.pavlov.cannons.Cannons;
import at.pavlov.cannons.projectile.ProjectileStorage;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;

public final class CannonProjectileConfiguration extends LoadableConfigurationSection<String> {
    public static String getConfigurationId() {
        return "CannonProjectile";
    }

    @Override
    public boolean shouldBeLoaded() {
        Plugin cannonsPlugin = Bukkit.getPluginManager().getPlugin("Cannons");
        return cannonsPlugin != null && cannonsPlugin.isEnabled() && cannonsPlugin instanceof Cannons;
    }

    @Override
    public String getEntityName(String entity) {
        return entity;
    }

    @Override
    public String getSectionPath() {
        return CannonProjectileConfiguration.getConfigurationId();
    }

    @Override
    public String getEntityFromName(String name) {
        return ProjectileStorage.getProjectile(name) != null ? name : null;
    }
}
