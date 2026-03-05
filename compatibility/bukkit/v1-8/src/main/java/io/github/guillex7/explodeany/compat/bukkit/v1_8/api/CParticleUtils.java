package io.github.guillex7.explodeany.compat.bukkit.v1_8.api;

import io.github.guillex7.explodeany.compat.bukkit.v1_8.data.CParticle;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IParticleUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyParticleData;

public class CParticleUtils implements IParticleUtils {
    @Override
    public CParticle createParticle(final EanyParticleData particleData) {
        return new CParticle();
    }
}
