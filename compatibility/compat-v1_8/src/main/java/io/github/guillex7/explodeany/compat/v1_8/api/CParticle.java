package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.World;

import io.github.guillex7.explodeany.compat.common.api.IParticle;

public class CParticle implements IParticle {
    @Override
    public void spawn(World world, double x, double y, double z, int count, double offsetX, double offsetY,
            double offsetZ,
            double speed, boolean force) {
        // Not supported
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String toString() {
        return "(Not supported)";
    }
}
