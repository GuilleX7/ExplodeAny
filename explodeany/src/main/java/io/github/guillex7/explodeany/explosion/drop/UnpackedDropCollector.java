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

    @Override
    public void collect(final Material material, final Location location) {
        this.collectedItems.add(new UnpackedDropItem(material, location));
    }

    @Override
    public void dropCollectedItems(final Location location) {
        for (final UnpackedDropItem item : this.collectedItems) {
            location.getWorld().dropItemNaturally(item.location, new ItemStack(item.material));
        }
    }
}
