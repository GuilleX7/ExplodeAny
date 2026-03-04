package io.github.guillex7.explodeany.compat.v1_8.data;

import org.bukkit.World;

import io.github.guillex7.explodeany.compat.common.data.IParticle;

public class CParticle implements IParticle {
    @Override
    public void spawn(final World world, final double x, final double y, final double z, final int count,
            final double offsetX, final double offsetY,
            final double offsetZ,
            final double speed, final boolean force) {
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
