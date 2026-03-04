package io.github.guillex7.explodeany.compat.v1_14.api;

import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;
import io.github.guillex7.explodeany.compat.v1_14.data.CParticle;

public class CParticleUtils extends io.github.guillex7.explodeany.compat.v1_13.api.CParticleUtils {
    @Override
    public CParticle createParticle(final EanyParticleData particleData) {
        final CParticle particle = new CParticle(particleData);
        particle.loadInternalParticle();
        return particle;
    }
}
