package io.github.guillex7.explodeany.compat.v1_13.api;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.data.BlockData;

import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;

public class CParticle extends io.github.guillex7.explodeany.compat.v1_9.api.CParticle {
    public CParticle(EanyParticleData particleData) {
        super(particleData);
    }

    @Override
    protected Object getExtraFromParticleData(Particle particle, EanyParticleData particleData) {
        Class<?> dataTypeClazz = this.getExtraTypeDataForParticle(particle);

        if (DustOptions.class.equals(dataTypeClazz)) {
            return this.getDustOptionsFromParticleData(particleData);
        } else if (BlockData.class.equals(dataTypeClazz)) {
            return this.getBlockDataFromParticleData(particleData);
        } else {
            return super.getExtraFromParticleData(particle, particleData);
        }
    }

    DustOptions getDustOptionsFromParticleData(EanyParticleData particleData) {
        return new DustOptions(Color.fromRGB(particleData.getRed(), particleData.getGreen(), particleData.getBlue()),
                (float) particleData.getSize());
    }

    BlockData getBlockDataFromParticleData(EanyParticleData particleData) {
        return particleData.getMaterial().createBlockData();
    }
}
