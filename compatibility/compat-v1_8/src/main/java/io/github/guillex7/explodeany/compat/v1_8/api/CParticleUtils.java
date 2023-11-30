package io.github.guillex7.explodeany.compat.v1_8.api;

import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.data.ParticleData;

public class CParticleUtils implements IParticleUtils {
    @Override
    public CParticle createParticle(ParticleData particleData) {
        return new CParticle();
    }
}
