package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.World;

public interface IParticle {
    void spawn(World world, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ,
            double speed,
            boolean force);
}
