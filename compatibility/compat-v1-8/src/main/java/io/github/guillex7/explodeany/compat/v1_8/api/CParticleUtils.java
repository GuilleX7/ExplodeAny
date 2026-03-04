package io.github.guillex7.explodeany.compat.v1_8.api;

import io.github.guillex7.explodeany.compat.common.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;
import io.github.guillex7.explodeany.compat.v1_8.data.CParticle;

public class CParticleUtils implements IParticleUtils {
    @Override
    public CParticle createParticle(final EanyParticleData particleData) {
        return new CParticle();
    }
}
