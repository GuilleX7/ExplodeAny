package io.github.guillex7.explodeany.explosion.drop;

import org.bukkit.Location;
import org.bukkit.Material;

public class UnpackedDropItem {
    Material material;
    Location location;

    public UnpackedDropItem(Material material, Location location) {
        this.material = material;
        this.location = location;
    }
}
