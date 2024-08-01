package io.github.guillex7.explodeany.compat.common.event;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public interface EanyBlockExplodeEvent {
    public Location getBlockLocation();

    public String getBlockMaterial();

    public List<Block> getBlockList();

    public boolean isCancelled();

    public void setCancelled(boolean cancelled);
}
