package io.github.guillex7.explodeany.compat.v1_13.api;

import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;

public class CParticleUtils implements IParticleUtils {
    @Override
    public CParticle createParticle(EanyParticleData particleData) {
        final CParticle particle = new CParticle(particleData);
        particle.loadInternalParticle();
        return particle;
    }
}
