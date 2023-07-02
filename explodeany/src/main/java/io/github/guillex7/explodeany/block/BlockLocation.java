package io.github.guillex7.explodeany.block;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockLocation {
    private UUID worldUuid;
    private int x;
    private int y;
    private int z;

    public static BlockLocation of(UUID worldUuid, int x, int y, int z) {
        return new BlockLocation(worldUuid, x, y, z);
    }

    public static BlockLocation fromLocation(Location location) {
        return of(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static BlockLocation fromBlock(Block block) {
        return of(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
    }

    private BlockLocation(UUID worldUuid, int x, int y, int z) {
        super();
        this.worldUuid = worldUuid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return Bukkit.getServer().getWorld(worldUuid);
    }

    public Location toLocation() {
        World world = getWorld();
        if (world == null) {
            return null;
        }
        return new Location(world, x, y, z);
    }

    public Block toBlock() {
        World world = getWorld();
        if (world == null) {
            return null;
        }
        return world.getBlockAt(x, y, z);
    }

    public UUID getWorldUuid() {
        return worldUuid;
    }

    public void setWorldUuid(UUID worldUuid) {
        this.worldUuid = worldUuid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
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

    @Override
    public String toString() {
        return "BlockLocation [worldUuid=" + worldUuid + ", x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}
