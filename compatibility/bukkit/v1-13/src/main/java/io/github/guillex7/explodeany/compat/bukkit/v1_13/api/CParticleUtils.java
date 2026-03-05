package io.github.guillex7.explodeany.compat.bukkit.v1_13.api;

import io.github.guillex7.explodeany.compat.bukkit.v1_13.data.CParticle;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyParticleData;

public class CParticleUtils extends io.github.guillex7.explodeany.compat.bukkit.v1_9.api.CParticleUtils {
    @Override
    public CParticle createParticle(final EanyParticleData particleData) {
        final CParticle particle = new CParticle(particleData);
        particle.loadInternalParticle();
        return particle;
    }
}
