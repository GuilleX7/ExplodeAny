package io.github.guillex7.explodeany.compat.v1_13.data;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.data.BlockData;

import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;

public class CParticle extends io.github.guillex7.explodeany.compat.v1_9.data.CParticle {
    public CParticle(final EanyParticleData particleData) {
        super(particleData);
    }

    @Override
    protected Object getExtraFromParticleData(final Particle particle, final EanyParticleData particleData) {
        final Class<?> dataTypeClazz = this.getExtraTypeDataForParticle(particle);

        if (DustOptions.class.equals(dataTypeClazz)) {
            return this.getDustOptionsFromParticleData(particleData);
        } else if (BlockData.class.equals(dataTypeClazz)) {
            return this.getBlockDataFromParticleData(particleData);
        } else {
            return super.getExtraFromParticleData(particle, particleData);
        }
    }

    DustOptions getDustOptionsFromParticleData(final EanyParticleData particleData) {
        return new DustOptions(Color.fromRGB(particleData.getRed(), particleData.getGreen(), particleData.getBlue()),
                (float) particleData.getSize());
    }

    BlockData getBlockDataFromParticleData(final EanyParticleData particleData) {
        return particleData.getMaterial().createBlockData();
    }
}
