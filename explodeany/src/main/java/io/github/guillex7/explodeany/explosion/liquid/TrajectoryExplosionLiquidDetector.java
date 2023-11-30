package io.github.guillex7.explodeany.explosion.liquid;

import org.bukkit.Location;
import org.bukkit.util.BlockIterator;

public class TrajectoryExplosionLiquidDetector {
    final BlockLiquidDetector blockLiquidDetector;

    public TrajectoryExplosionLiquidDetector() {
        this.blockLiquidDetector = new BlockLiquidDetector();
    }

    public boolean isLiquidInTrajectory(Location sourceLocation, Location targetLocation) {
        if (sourceLocation.equals(targetLocation)) {
            return this.blockLiquidDetector.isBlockLiquidlike(sourceLocation);
        }

        BlockIterator iterator = new BlockIterator(sourceLocation.getWorld(), sourceLocation.toVector(),
                targetLocation.toVector().subtract(sourceLocation.toVector()), 0,
                (int) sourceLocation.distance(targetLocation));

        while (iterator.hasNext()) {
            if (this.blockLiquidDetector.isBlockLiquidlike(iterator.next())) {
                return true;
            }
        }
        return false;
    }
}
