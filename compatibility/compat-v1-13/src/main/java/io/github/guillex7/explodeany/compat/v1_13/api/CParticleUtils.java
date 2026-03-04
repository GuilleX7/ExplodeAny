package io.github.guillex7.explodeany.compat.v1_13.api;

import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;
import io.github.guillex7.explodeany.compat.v1_13.data.CParticle;

public class CParticleUtils extends io.github.guillex7.explodeany.compat.v1_9.api.CParticleUtils {
    @Override
    public CParticle createParticle(final EanyParticleData particleData) {
        final CParticle particle = new CParticle(particleData);
        particle.loadInternalParticle();
        return particle;
    }
}
