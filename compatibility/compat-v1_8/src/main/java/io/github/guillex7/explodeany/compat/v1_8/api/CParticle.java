package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.World;

import io.github.guillex7.explodeany.compat.common.api.IParticle;

public class CParticle implements IParticle {
    @Override
    public void spawn(World world, double x, double y, double z, int count, double offsetX, double offsetY,
            double offsetZ,
            double extra, boolean force) {
        // Not supported
    }
}
