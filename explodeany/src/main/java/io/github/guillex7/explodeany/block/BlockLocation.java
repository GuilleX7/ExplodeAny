package io.github.guillex7.explodeany.block;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockLocation {
    private final UUID worldUuid;
    private final int x;
    private final int y;
    private final int z;

    public static BlockLocation fromBlock(final Block block) {
        return new BlockLocation(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
    }

    public BlockLocation(final UUID worldUuid, final int x, final int y, final int z) {
        this.worldUuid = worldUuid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return Bukkit.getServer().getWorld(this.worldUuid);
    }

    public Block toBlock() {
        final World world = this.getWorld();
        if (world == null) {
            return null;
        }
        return world.getBlockAt(this.x, this.y, this.z);
    }

    public UUID getWorldUuid() {
        return this.worldUuid;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.worldUuid == null) ? 0 : this.worldUuid.hashCode());
        result = prime * result + this.x;
        result = prime * result + this.y;
        result = prime * result + this.z;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final BlockLocation other = (BlockLocation) obj;
        if (this.worldUuid == null) {
            if (other.worldUuid != null) {
                return false;
            }
        } else if (!this.worldUuid.equals(other.worldUuid)) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }
}
