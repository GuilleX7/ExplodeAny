package io.github.guillex7.explodeany.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Material;

import io.github.guillex7.explodeany.ExplodeAny;

public class NamePatternUtils {
    private NamePatternUtils() {
    }

    public static Pattern getPatternFromNamePattern(String namePattern, boolean isCaseSensitive) {
        namePattern = namePattern.replace("*", ".*");

        try {
            return Pattern.compile(namePattern, isCaseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            ExplodeAny.getInstance().getLogger().
                    warning(String.format("Couldn't parse the name pattern: %s", namePattern));
            return null;
        }
    }

    public static boolean isNamePattern(String name) {
        return name.contains("*");
    }

    public static final Material getMaterialFromName(String name) {
        Material material = Material.getMaterial(name.toUpperCase());
        return material;
    }

    public static List<Material> getMaterialsFromPattern(Pattern pattern) {
        return Arrays.stream(Material.values()).filter(material -> pattern.matcher(material.name()).matches())
                .collect(Collectors.toList());
    }

    public static List<Material> getMaterialsFromNameOrPattern(String nameOrPattern) {
        if (NamePatternUtils.isNamePattern(nameOrPattern)) {
            Pattern pattern = NamePatternUtils.getPatternFromNamePattern(nameOrPattern, false);
            if (pattern == null) {
                return new ArrayList<>();
            }

            return NamePatternUtils.getMaterialsFromPattern(pattern);
        } else {
            Material material = NamePatternUtils.getMaterialFromName(nameOrPattern);
            return material != null ? Arrays.asList(material) : new ArrayList<>();
        }
    }
}
