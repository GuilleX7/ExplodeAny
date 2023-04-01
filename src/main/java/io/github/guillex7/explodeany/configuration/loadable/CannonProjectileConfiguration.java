package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.Bukkit;

import at.pavlov.cannons.projectile.ProjectileStorage;

public final class CannonProjectileConfiguration extends LoadableSectionConfiguration<String> {
	@Override
	public boolean shouldBeLoaded() {
		return Bukkit.getPluginManager().isPluginEnabled("Cannons");
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
