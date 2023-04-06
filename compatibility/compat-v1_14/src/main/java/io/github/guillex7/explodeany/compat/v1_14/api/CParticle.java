package io.github.guillex7.explodeany.compat.v1_14.api;

import org.bukkit.World;

public class CParticle extends io.github.guillex7.explodeany.compat.v1_9.api.CParticle {
    public CParticle(ParticleData particleData) {
        super(particleData);
    }

    @Override
    public void spawn(World world, double x, double y, double z, int count, double offsetX, double offsetY,
            double offsetZ, double speed, boolean force) {
        if (this.particle != null) {
            world.spawnParticle(this.particle, x, y, z, count, offsetX, offsetY, offsetZ, speed, this.extra, force);
        }
    }
}
