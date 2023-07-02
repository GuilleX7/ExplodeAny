package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.entity.EntityType;

public interface IExplosionUtils {
    ExplosionParameters getExplosionRadiusAndPower(EntityType entityType, boolean isCharged);

    public class ExplosionParameters {
        private int radius;
        private float power;

        public ExplosionParameters(int radius, float power) {
            this.radius = radius;
            this.power = power;
        }

        public int getRadius() {
            return radius;
        }

        public float getPower() {
            return power;
        }

        public boolean areValid() {
            return radius != 0;
        }
    }

}
