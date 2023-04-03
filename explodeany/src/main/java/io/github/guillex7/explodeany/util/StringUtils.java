package io.github.guillex7.explodeany.util;

public class StringUtils {
    public static String beautifyName(String name) {
        if (name.length() < 1) {
            return name;
        }

        return String.format("%s%s",
                name.substring(0, 1).toUpperCase(),
                name.substring(1).toLowerCase())
                .replaceAll("_", " ");
    }
}
