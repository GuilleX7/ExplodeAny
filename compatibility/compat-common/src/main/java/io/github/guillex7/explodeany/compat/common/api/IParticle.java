package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.Material;
import org.bukkit.World;

public interface IParticle {
    void spawn(World world, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ,
            double speed,
            boolean force);

    public class ParticleData {
        private String name;
        private int red;
        private int green;
        private int blue;
        private float size;
        private Material material;

        public ParticleData(String name, int red, int green, int blue, float size, Material material) {
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

        public float getSize() {
            return size;
        }

        public void setSize(float size) {
            this.size = size;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }
    }

}