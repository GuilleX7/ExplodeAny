package io.github.guillex7.explodeany.compat.v1_8.api;

import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;

public class CParticleUtils implements IParticleUtils {
    @Override
    public CParticle createParticle(EanyParticleData particleData) {
        return new CParticle();
    }
}
