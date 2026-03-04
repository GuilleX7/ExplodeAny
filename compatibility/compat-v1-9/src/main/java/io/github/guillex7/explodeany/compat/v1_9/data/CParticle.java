package io.github.guillex7.explodeany.compat.v1_9.data;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import io.github.guillex7.explodeany.compat.common.data.EanyParticleData;

public class CParticle extends io.github.guillex7.explodeany.compat.v1_8.data.CParticle {
    protected EanyParticleData particleData;
    protected Particle particle;
    protected Object extra;

    public CParticle(final EanyParticleData particleData) {
        this.particleData = particleData;
    }

    public void loadInternalParticle() {
        this.particle = this.getParticleFromParticleData(this.particleData);
        if (this.particle != null) {
            this.extra = this.getExtraFromParticleData(this.particle, this.particleData);
            if (this.isExtraRequiredForParticle(this.particle) && this.extra == null) {
                this.particle = null;
            }
        }
    }

    protected Object getExtraFromParticleData(final Particle particle, final EanyParticleData particleData) {
        final Class<?> dataTypeClazz = this.getExtraTypeDataForParticle(particle);

        if (ItemStack.class.equals(dataTypeClazz)) {
            return this.getItemStackFromMaterial(particleData.getMaterial());
        } else if (MaterialData.class.equals(dataTypeClazz)) {
            return this.getMaterialDataFromMaterial(particleData.getMaterial());
        } else {
            return null;
        }
    }

    protected Class<?> getExtraTypeDataForParticle(final Particle particle) {
        return particle.getDataType();
    }

    protected boolean isExtraRequiredForParticle(final Particle particle) {
        return !Void.class.equals(this.getExtraTypeDataForParticle(particle));
    }

    protected Particle getParticleFromParticleData(final EanyParticleData particleData) {
        try {
            return Particle.valueOf(particleData.getName());
        } catch (final Exception e) {
            return null;
        }
    }

    protected ItemStack getItemStackFromMaterial(final Material material) {
        try {
            return new ItemStack(material);
        } catch (final Exception e) {
            return null;
        }
    }

    protected MaterialData getMaterialDataFromMaterial(final Material material) {
        try {
            return new MaterialData(material);
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public void spawn(final World world, final double x, final double y, final double z, final int count,
            final double offsetX, final double offsetY,
            final double offsetZ,
            final double speed, final boolean force) {
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
