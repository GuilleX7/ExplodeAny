package io.github.guillex7.explodeany.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackUtils {
    private ItemStackUtils() {
    }

    public static boolean areItemStacksSimilar(ItemStack itemA, ItemStack itemB) {
        if (itemA.getType().equals(Material.AIR) && itemB.getType().equals(Material.AIR)) {
            return true;
        } else {
            return itemA.isSimilar(itemB);
        }
    }
}
