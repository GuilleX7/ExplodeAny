package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.Bukkit;

import at.pavlov.cannons.projectile.ProjectileStorage;

public final class CannonProjectileConfiguration extends LoadableSectionConfiguration<String> {
	private static CannonProjectileConfiguration instance;
	
	private CannonProjectileConfiguration() {}
	
	public static CannonProjectileConfiguration getInstance() {
		if (instance == null) {
			instance = new CannonProjectileConfiguration();
		}
		return instance;
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
		return ProjectileStorage.getProjectile(entity) != null;
	}
}
