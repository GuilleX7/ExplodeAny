package io.github.guillex7.explodeany.explosion.liquid;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;

public class TrajectoryExplosionLiquidDetector {
    public static boolean isLiquidInTrajectory(Location sourceBlockLocation, Location targetBlockLocation,
            int explosionRadius) {
        if (sourceBlockLocation.equals(targetBlockLocation)) {
            return BlockLiquidDetector.isLocationLiquidlike(sourceBlockLocation);
        }

        BlockIterator iterator = new BlockIterator(sourceBlockLocation.getWorld(), sourceBlockLocation.toVector(),
                targetBlockLocation.toVector().subtract(sourceBlockLocation.toVector()), 0,
                explosionRadius);

        while (iterator.hasNext()) {
            Block nextBlock = iterator.next();
            if (BlockLiquidDetector.isBlockLiquidlike(nextBlock)) {
                return true;
            } else if (nextBlock.getLocation().equals(targetBlockLocation)) {
                return false;
            }
        }
        return false;
    }
}
