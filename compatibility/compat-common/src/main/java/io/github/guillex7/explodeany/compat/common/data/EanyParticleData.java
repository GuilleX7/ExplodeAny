package io.github.guillex7.explodeany.compat.common.data;

import org.bukkit.Material;

public class EanyParticleData {
    private String name;
    private int red;
    private int green;
    private int blue;
    private double size;
    private Material material;

    public EanyParticleData(final String name, final int red, final int green, final int blue, final double size,
            final Material material) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.size = size;
        this.material = material;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getRed() {
        return this.red;
    }

    public void setRed(final int red) {
        this.red = red;
    }

    public int getGreen() {
        return this.green;
    }

    public void setGreen(final int green) {
        this.green = green;
    }

    public int getBlue() {
        return this.blue;
    }

    public void setBlue(final int blue) {
        this.blue = blue;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(final double size) {
        this.size = size;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(final Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\n"
                + "R: %d G: %d B: %d\n"
                + "Size: %.2f\n"
                + "Material: %s", this.name, this.red, this.green, this.blue, this.size,
                this.material != null ? this.material.toString() : "(None)");
    }
}
