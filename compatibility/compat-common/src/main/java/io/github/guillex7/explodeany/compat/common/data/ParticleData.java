package io.github.guillex7.explodeany.compat.common.data;

import org.bukkit.Material;

public class ParticleData {
    private String name;
    private int red;
    private int green;
    private int blue;
    private double size;
    private Material material;

    public ParticleData(String name, int red, int green, int blue, double size, Material material) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.size = size;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\n"
                + "R: %d G: %d B: %d\n"
                + "Size: %.2f\n"
                + "Material: %s", name, red, green, blue, size, material != null ? material.toString() : "(None)");
    }
}