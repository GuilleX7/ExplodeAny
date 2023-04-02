package io.github.guillex7.explodeany.listener.loadable.explosion;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;

public final class VanillaExplosionListener extends VanillaBaseExplosionListener {
	public static boolean isEntityVanilla(Entity entity) {
		return entity.getPersistentDataContainer().isEmpty();
	}

	@Override
	public String getName() {
		return "Vanilla explosions";
	}

	@Override
	public boolean isAdvisable() {
		return true;
	}

	@Override
	protected boolean isEventHandled(EntityExplodeEvent event) {
		Entity entity = event.getEntity();
		return entity == null || isEntityVanilla(entity);
	}

	@Override
	protected LoadableConfigurationSection<?> getConfiguration() {
		return ConfigurationManager.getInstance()
				.getRegisteredEntityConfiguration("VanillaEntity");
	}
}
