package io.github.guillex7.explodeany.compat.bukkit.v1_8.api;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.guillex7.explodeany.compat.common.bukkit.api.IPlayerInventoryUtils;

public class CPlayerInventoryUtils implements IPlayerInventoryUtils {
    @Override
    public ItemStack getItemInMainHand(final PlayerInventory inventory) {
        return inventory.getItemInHand();
    }
}
