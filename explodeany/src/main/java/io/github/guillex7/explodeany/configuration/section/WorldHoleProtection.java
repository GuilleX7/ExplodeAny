package io.github.guillex7.explodeany.configuration.section;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.util.SetUtils;

public class WorldHoleProtection {
    private static final String HEIGHTS_PATH = "Heights";
    private static final String PROTECT_UNHANDLED_BLOCKS_PATH = "ProtectUnhandledBlocks";

    private final Set<Integer> heights;
    private final boolean protectUnhandledBlocks;

    public static WorldHoleProtection of(final Set<Integer> heights, final boolean protectUnhandledBlocks) {
        return new WorldHoleProtection(heights, protectUnhandledBlocks);
    }

    public static WorldHoleProtection byDefault() {
        return new WorldHoleProtection(SetUtils.createHashSetOf(), false);
    }

    public static WorldHoleProtection fromConfigSection(final ConfigurationSection section) {
        final WorldHoleProtection defaults = WorldHoleProtection.byDefault();

        return new WorldHoleProtection(new HashSet<>(section.getIntegerList(WorldHoleProtection.HEIGHTS_PATH)),
                section.getBoolean(WorldHoleProtection.PROTECT_UNHANDLED_BLOCKS_PATH, defaults.protectUnhandledBlocks));
    }

    private WorldHoleProtection(final Set<Integer> heights, final boolean protectUnhandledBlocks) {
        this.heights = heights;
        this.protectUnhandledBlocks = protectUnhandledBlocks;
    }

    public boolean isHeightProtected(final int height) {
        return this.heights.contains(height);
    }

    public boolean doProtectUnhandledBlocks() {
        return this.protectUnhandledBlocks;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("&fHeights: ");
        if (!this.heights.isEmpty()) {
            builder.append(this.heights.stream().map(Object::toString).collect(Collectors.joining(", ")));
        } else {
            builder.append("none");
        }
        builder.append("\n");
        builder.append("Protect unhandled blocks: ").append(this.protectUnhandledBlocks);
        return builder.toString();
    }
}
