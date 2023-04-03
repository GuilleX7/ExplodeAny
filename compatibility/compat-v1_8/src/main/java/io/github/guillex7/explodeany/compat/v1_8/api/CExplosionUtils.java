package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.entity.EntityType;

import io.github.guillex7.explodeany.compat.common.api.IExplosionUtils;

public class CExplosionUtils implements IExplosionUtils {
    @Override
    public ExplosionParameters getExplosionRadiusAndPower(EntityType entityType, boolean isCharged) {
        // Magic values come from https://minecraft.gamepedia.com/Explosion
        switch (entityType) {
            case WITHER: // Wither spawn or destroy
                return new ExplosionParameters(7, 7f);
            case ENDER_CRYSTAL: // End crystal
                return new ExplosionParameters(6, 6f);
            case PRIMED_TNT: // TNT
                return new ExplosionParameters(4, 4f);
            case CREEPER: // Creeper explosion
                return isCharged ? new ExplosionParameters(4, 6f) : new ExplosionParameters(3, 3f);
            case FIREBALL: // Large fireball (Ghast)
            case SMALL_FIREBALL: // Blaze fireball
            case WITHER_SKULL: // Wither skull explosion
                return new ExplosionParameters(1, 1f);
            default:
                return new ExplosionParameters(0, 0);
        }
    }
}
