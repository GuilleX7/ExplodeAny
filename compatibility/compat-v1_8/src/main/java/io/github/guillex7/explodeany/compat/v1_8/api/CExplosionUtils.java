package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.entity.EntityType;

import io.github.guillex7.explodeany.compat.common.api.IExplosionUtils;

public class CExplosionUtils implements IExplosionUtils {
    @Override
    public double getExplosionRadiusAndPower(EntityType entityType, boolean isCharged) {
        // Magic values come from https://minecraft.gamepedia.com/Explosion
        switch (entityType) {
            case WITHER: // Wither spawn or destroy
                return 7d;
            case ENDER_CRYSTAL: // End crystal
                return 6d;
            case PRIMED_TNT: // TNT
            case MINECART_TNT: // TNT minecart
                return 4d;
            case CREEPER: // Creeper explosion
                return isCharged ? 5d : 3d;
            case FIREBALL: // Large fireball (Ghast)
            case SMALL_FIREBALL: // Blaze fireball
            case WITHER_SKULL: // Wither skull explosion
                return 1d;
            default:
                return 0d;
        }
    }
}
