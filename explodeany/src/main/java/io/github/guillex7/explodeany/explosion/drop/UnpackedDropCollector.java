package io.github.guillex7.explodeany.explosion.drop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class UnpackedDropCollector implements DropCollector {
    final List<UnpackedDropItem> collectedItems;

    public UnpackedDropCollector() {
        this.collectedItems = new ArrayList<>();
    }

    public void collect(Material material, Location location) {
        collectedItems.add(new UnpackedDropItem(material, location));
    }

    public void dropCollectedItems(Location location) {
        for (UnpackedDropItem item : collectedItems) {
            location.getWorld().dropItemNaturally(item.location, new ItemStack(item.material));
        }
    }
}
