package io.github.guillex7.explodeany.compat.v1_9.api;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import io.github.guillex7.explodeany.compat.common.api.IParticle;

public class CParticle implements IParticle {
    protected Particle particle;
    protected Object extra;

    public CParticle(ParticleData particleData) {
        this.particle = getParticleFromParticleData(particleData);
        if (this.particle != null) {
            this.extra = getExtraFromParticleData(this.particle, particleData);
            if (isExtraRequiredForParticle(this.particle) && this.extra == null) {
                this.particle = null;
            }
        }
    }

    protected Object getExtraFromParticleData(Particle particle, ParticleData particleData) {
        Class<?> dataTypeClazz = getExtraTypeDataForParticle(particle);

        if (ItemStack.class.equals(dataTypeClazz)) {
            return getItemStackFromMaterial(particleData.getMaterial());
        } else if (MaterialData.class.equals(dataTypeClazz)) {
            return getMaterialDataFromMaterial(particleData.getMaterial());
        } else {
            return null;
        }
    }

    protected Class<?> getExtraTypeDataForParticle(Particle particle) {
        return this.particle.getDataType();
    }

    protected boolean isExtraRequiredForParticle(Particle particle) {
        return !Void.class.equals(getExtraTypeDataForParticle(particle));
    }

    protected Particle getParticleFromParticleData(ParticleData particleData) {
        try {
            return Particle.valueOf(particleData.getName());
        } catch (Exception e) {
            return null;
        }
    }

    protected ItemStack getItemStackFromMaterial(Material material) {
        try {
            return new ItemStack(material);
        } catch (Exception e) {
            return null;
        }
    }

    protected MaterialData getMaterialDataFromMaterial(Material material) {
        try {
            return new MaterialData(material);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void spawn(World world, double x, double y, double z, int count, double offsetX, double offsetY,
            double offsetZ,
            double extra, boolean force) {
        if (this.particle != null) {
            world.spawnParticle(this.particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, this.extra);
        }
    }
}
