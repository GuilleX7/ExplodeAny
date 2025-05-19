package io.github.guillex7.explodeany.configuration.loadable.cannon;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public String getHumanReadableName() {
        return "Cannons projectiles";
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

    @Override
    public List<String> getEntitiesFromPattern(Pattern pattern, String name) {
        return ProjectileStorage.getProjectileIds().stream()
                .filter(projectileId -> pattern.matcher(projectileId).matches())
                .collect(Collectors.toList());
    }
}
