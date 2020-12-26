package io.github.guillex7.explodeany.listener.loadable;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.LoadableSectionConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;

public final class VanillaExplosionListener implements LoadableListener {
	private VanillaExplosionListener() {
		super();
	}

	public static VanillaExplosionListener empty() {
		return new VanillaExplosionListener();
	}

	@Override
	public String getName() {
		return "Vanilla explosions";
	}

	@Override
	public boolean shouldBeLoaded() {
		return ConfigurationManager.getInstance().getRegisteredEntityConfiguration("VanillaEntity").shouldBeLoaded();
	}

	@Override
	public boolean isAdvisable() {
		return true;
	}

	@EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntity() == null) {
			return;
		}

		String entityTypeName = event.getEntityType().toString();
		boolean isCharged = false;
		Map<Material, EntityMaterialConfiguration> materialConfigurations = null;

		// Special cases
		if (event.getEntityType().equals(EntityType.CREEPER)) {
			Creeper creeper = (Creeper) event.getEntity();
			if (creeper.isPowered()) {
				entityTypeName = "CHARGED_".concat(entityTypeName);
				isCharged = true;
			}
		} else if (event.getEntityType().equals(EntityType.WITHER_SKULL)) {
			WitherSkull witherSkull = (WitherSkull) event.getEntity();
			if (witherSkull.isCharged()) {
				entityTypeName = "CHARGED_".concat(entityTypeName);
				isCharged = true;
			}
		}

		LoadableSectionConfiguration<?> vanillaEntityConfiguration = ConfigurationManager.getInstance()
				.getRegisteredEntityConfiguration("VanillaEntity");

		materialConfigurations = vanillaEntityConfiguration.getEntityMaterialConfigurations().get(entityTypeName);
		if (materialConfigurations == null) {
			return;
		}

		EntityConfiguration entityConfiguration = vanillaEntityConfiguration.getEntityConfigurations()
				.get(entityTypeName);

		// Magic values come from https://minecraft.gamepedia.com/Explosion
		int explosionRadius = 0;
		switch (event.getEntityType()) {
		case WITHER: // Wither spawn or destroy
			explosionRadius = 7;
			break;
		case ENDER_CRYSTAL: // End crystal
			explosionRadius = 6;
			break;
		case PRIMED_TNT:
			explosionRadius = 4; // TNT
			break;
		case CREEPER: // Creeper explosion
			explosionRadius = (isCharged) ? 4 : 3;
			break;
		case FIREBALL: // Large fireball (Ghast)
		case DRAGON_FIREBALL: // Dragon fireball
		case SMALL_FIREBALL: // Blaze fireball
		case WITHER_SKULL: // Wither skull explosion
			explosionRadius = 1;
			break;
		default:
			break; // Unsupported entity type
		}

		if (explosionRadius <= 0) {
			return;
		}

		ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.blockList());
		ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration, event.getLocation(), explosionRadius);
	}

	@Override
	public void unload() {
		EntityExplodeEvent.getHandlerList().unregister(this);
	}
}
