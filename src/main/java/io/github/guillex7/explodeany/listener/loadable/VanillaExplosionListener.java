package io.github.guillex7.explodeany.listener.loadable;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.configuration.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.VanillaEntityConfiguration;
import io.github.guillex7.explodeany.explosion.ExplosionManager;

public final class VanillaExplosionListener implements LoadableExplosionListener {
	private static VanillaExplosionListener instance;
	
	private VanillaExplosionListener() {}
	
	public static VanillaExplosionListener getInstance() {
		if (instance == null) {
			instance = new VanillaExplosionListener();
		}
		return instance;
	}

	@Override
	public String getName() {
		return "Vanilla";
	}
	
	@Override
	public boolean shouldBeLoaded() {
		return VanillaEntityConfiguration.getInstance().shouldBeLoaded();
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
		
		materialConfigurations = VanillaEntityConfiguration.getInstance()
			.getEntityMaterialConfigurations().get(entityTypeName);
		if (materialConfigurations == null) {
			return;
		}
		
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
		
		ExplosionManager.removeHandledBlocksFromList(materialConfigurations, event.blockList());
		ExplosionManager.manageExplosion(materialConfigurations, event.getLocation(), explosionRadius);
	}

	@Override
	public void unload() {
		EntityExplodeEvent.getHandlerList().unregister(this);
	}
}
