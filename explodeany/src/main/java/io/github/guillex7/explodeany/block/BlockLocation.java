package io.github.guillex7.explodeany.block;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockLocation {
    private UUID worldUuid;
    private int x;
    private int y;
    private int z;

    public static BlockLocation fromBlock(Block block) {
        return new BlockLocation(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
    }

    public BlockLocation(UUID worldUuid, int x, int y, int z) {
        this.worldUuid = worldUuid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return Bukkit.getServer().getWorld(this.worldUuid);
    }

    public Block toBlock() {
        World world = this.getWorld();
        if (world == null) {
            return null;
        }
        return world.getBlockAt(this.x, this.y, this.z);
    }

    public UUID getWorldUuid() {
        return worldUuid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((worldUuid == null) ? 0 : worldUuid.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlockLocation other = (BlockLocation) obj;
        if (worldUuid == null) {
            if (other.worldUuid != null)
                return false;
        } else if (!worldUuid.equals(other.worldUuid))
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }
}
