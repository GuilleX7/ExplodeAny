package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import at.pavlov.cannons.Cannons;
import at.pavlov.cannons.projectile.ProjectileStorage;

public final class CannonProjectileConfiguration extends LoadableConfigurationSection<String> {
    @Override
    public boolean shouldBeLoaded() {
        Plugin externalPlugin = Bukkit.getPluginManager().getPlugin("Cannons");
        return externalPlugin != null && externalPlugin instanceof Cannons;
    }

    @Override
    public String getEntityName(String entity) {
        return entity;
    }

    @Override
    public String getSectionPath() {
        return "CannonProjectile";
    }

    @Override
    public String getEntityFromName(String name) {
        return name;
    }

    @Override
    public boolean isEntityTypeValid(String entity) {
        return entity != null && ProjectileStorage.getProjectile(entity) != null;
    }
}
