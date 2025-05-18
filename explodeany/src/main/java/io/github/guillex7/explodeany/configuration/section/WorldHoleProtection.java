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

    public static WorldHoleProtection of(Set<Integer> heights, boolean protectUnhandledBlocks) {
        return new WorldHoleProtection(heights, protectUnhandledBlocks);
    }

    public static WorldHoleProtection byDefault() {
        return new WorldHoleProtection(SetUtils.createHashSetOf(), false);
    }

    public static WorldHoleProtection fromConfigSection(ConfigurationSection section) {
        final WorldHoleProtection defaults = WorldHoleProtection.byDefault();

        return new WorldHoleProtection(new HashSet<>(section.getIntegerList(HEIGHTS_PATH)),
                section.getBoolean(PROTECT_UNHANDLED_BLOCKS_PATH, defaults.protectUnhandledBlocks));
    }

    private WorldHoleProtection(Set<Integer> heights, boolean protectUnhandledBlocks) {
        this.heights = heights;
        this.protectUnhandledBlocks = protectUnhandledBlocks;
    }

    public boolean isHeightProtected(int height) {
        return heights.contains(height);
    }

    public boolean doProtectUnhandledBlocks() {
        return protectUnhandledBlocks;
    }

    @Override
    public String toString() {
        return String.format(
                "&fHeights: %s\n"
                + "Protect unhandled blocks: %s",
                !heights.isEmpty() ? heights.stream().map(x -> x.toString()).collect(Collectors.joining(", ")) : "none",
                protectUnhandledBlocks);
    }
}
