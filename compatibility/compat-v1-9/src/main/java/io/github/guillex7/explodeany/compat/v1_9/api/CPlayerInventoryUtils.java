package io.github.guillex7.explodeany.compat.v1_9.api;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CPlayerInventoryUtils extends io.github.guillex7.explodeany.compat.v1_8_3.api.CPlayerInventoryUtils {
    @Override
    public ItemStack getItemInMainHand(final PlayerInventory inventory) {
        return inventory.getItemInMainHand();
    }
}
