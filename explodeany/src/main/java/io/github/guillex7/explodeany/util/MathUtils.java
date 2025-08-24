package io.github.guillex7.explodeany.util;

public class MathUtils {
    private MathUtils() {
    }

    public static double ensureRange(final double value, final double max, final double min) {
        return Math.min(Math.max(value, min), max);
    }

    public static double ensureMax(final double value, final double max) {
        return Math.min(value, max);
    }

    public static double ensureMin(final double value, final double min) {
        return Math.max(value, min);
    }

    public static int ensureRange(final int value, final int max, final int min) {
        return Math.min(Math.max(value, min), max);
    }

    public static int ensureMax(final int value, final int max) {
        return Math.min(value, max);
    }

    public static int ensureMin(final int value, final int min) {
        return Math.max(value, min);
    }
}
