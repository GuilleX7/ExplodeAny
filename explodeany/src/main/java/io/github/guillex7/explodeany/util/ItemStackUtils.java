package io.github.guillex7.explodeany.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackUtils {
    private ItemStackUtils() {
    }

    public static boolean areItemStacksSimilar(final ItemStack itemA, final ItemStack itemB) {
        if (Material.AIR.equals(itemA.getType()) && Material.AIR.equals(itemB.getType())) {
            return true;
        } else {
            return itemA.isSimilar(itemB);
        }
    }
}
