package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public interface IPlayerInventoryUtils {
    ItemStack getItemInMainHand(PlayerInventory inventory);
}
