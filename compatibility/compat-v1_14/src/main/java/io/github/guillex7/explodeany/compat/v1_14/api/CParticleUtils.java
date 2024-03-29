package io.github.guillex7.explodeany.compat.v1_14.api;

import io.github.guillex7.explodeany.compat.common.data.ParticleData;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;

public class CParticleUtils implements IParticleUtils {
    @Override
    public CParticle createParticle(ParticleData particleData) {
        return new CParticle(particleData);
    }
}
