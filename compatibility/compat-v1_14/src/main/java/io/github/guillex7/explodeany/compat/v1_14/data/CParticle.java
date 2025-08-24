package io.github.guillex7.explodeany.compat.v1_14.data;

import org.bukkit.World;

import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;

public class CParticle extends io.github.guillex7.explodeany.compat.v1_13.data.CParticle {
    public CParticle(final EanyParticleData particleData) {
        super(particleData);
    }

    @Override
    public void spawn(final World world, final double x, final double y, final double z, final int count,
            final double offsetX, final double offsetY,
            final double offsetZ, final double speed, final boolean force) {
        if (this.particle != null) {
            world.spawnParticle(this.particle, x, y, z, count, offsetX, offsetY, offsetZ, speed, this.extra, force);
        }
    }
}
