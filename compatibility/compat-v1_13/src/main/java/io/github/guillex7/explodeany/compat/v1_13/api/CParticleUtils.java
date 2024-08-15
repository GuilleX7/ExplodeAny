package io.github.guillex7.explodeany.compat.v1_13.api;

import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;
import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;

public class CParticleUtils implements IParticleUtils {
    @Override
    public CParticle createParticle(EanyParticleData particleData) {
        return new CParticle(particleData);
    }
}
