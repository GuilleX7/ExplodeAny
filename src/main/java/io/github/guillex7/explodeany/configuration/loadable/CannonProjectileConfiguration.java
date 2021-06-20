package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.Bukkit;

import at.pavlov.cannons.projectile.ProjectileStorage;

public final class CannonProjectileConfiguration extends LoadableSectionConfiguration<String> {
	private CannonProjectileConfiguration() {
		super();
	}

	public static CannonProjectileConfiguration empty() {
		return new CannonProjectileConfiguration();
	}

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
	public boolean checkEntityTypeIsValid(String entity) {
		return entity != null && ProjectileStorage.getProjectile(entity) != null;
	}
}
