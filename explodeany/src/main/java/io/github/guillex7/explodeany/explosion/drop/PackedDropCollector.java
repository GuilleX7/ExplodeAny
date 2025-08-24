package io.github.guillex7.explodeany.explosion.drop;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;

public class PackedDropCollector implements DropCollector {
    final Map<Material, Integer> collectedItems;

    public PackedDropCollector(final Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        this.collectedItems = new HashMap<>(materialConfigurations.size(), 1);
    }

    @Override
    public void collect(final Material material, final Location location) {
        this.collectedItems.put(material, this.collectedItems.getOrDefault(material, 0) + 1);
    }

    @Override
    public void dropCollectedItems(final Location location) {
        final World world = location.getWorld();
        for (final Map.Entry<Material, Integer> entry : this.collectedItems.entrySet()) {
            world.dropItemNaturally(location, new ItemStack(entry.getKey(), entry.getValue()));
        }
    }
}
