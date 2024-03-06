package io.github.guillex7.explodeany.compat.common.event;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import io.github.guillex7.explodeany.compat.common.data.ExplodingVanillaMaterial;

public interface EanyBlockExplodeEvent {
    public Location getBlockLocation();

    public ExplodingVanillaMaterial getBlockMaterial();

    public List<Block> getBlockList();

    public boolean isCancelled();

    public void setCancelled(boolean cancelled);
}
