package io.github.guillex7.explodeany.explosion.drop;

import org.bukkit.Location;
import org.bukkit.Material;

public interface DropCollector {
    void collect(Material material, Location location);
}
