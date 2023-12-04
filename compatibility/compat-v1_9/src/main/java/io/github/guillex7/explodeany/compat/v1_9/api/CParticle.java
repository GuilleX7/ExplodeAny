package io.github.guillex7.explodeany.compat.v1_9.api;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import io.github.guillex7.explodeany.compat.common.api.IParticle;
import io.github.guillex7.explodeany.compat.common.data.ParticleData;

public class CParticle implements IParticle {
    protected ParticleData particleData;
    protected Particle particle;
    protected Object extra;

    public CParticle(ParticleData particleData) {
        this.particleData = particleData;
        this.particle = this.getParticleFromParticleData(particleData);
        if (this.particle != null) {
            this.extra = this.getExtraFromParticleData(this.particle, particleData);
            if (this.isExtraRequiredForParticle(this.particle) && this.extra == null) {
                this.particle = null;
            }
        }
    }

    protected Object getExtraFromParticleData(Particle particle, ParticleData particleData) {
        Class<?> dataTypeClazz = this.getExtraTypeDataForParticle(particle);

        if (ItemStack.class.equals(dataTypeClazz)) {
            return this.getItemStackFromMaterial(particleData.getMaterial());
        } else if (MaterialData.class.equals(dataTypeClazz)) {
            return this.getMaterialDataFromMaterial(particleData.getMaterial());
        } else {
            return null;
        }
    }

    protected Class<?> getExtraTypeDataForParticle(Particle particle) {
        return particle.getDataType();
    }

    protected boolean isExtraRequiredForParticle(Particle particle) {
        return !Void.class.equals(this.getExtraTypeDataForParticle(particle));
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
            double speed, boolean force) {
        if (this.isValid()) {
            world.spawnParticle(this.particle, x, y, z, count, offsetX, offsetY, offsetZ, speed, this.extra);
        }
    }

    @Override
    public boolean isValid() {
        return this.particle != null;
    }
    
    @Override
    public String toString() {
        return this.isValid() ? this.particleData.toString() : "(None)";
    }
}
