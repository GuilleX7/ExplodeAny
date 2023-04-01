package io.github.guillex7.explodeany.util;

public class MathUtils {
    public static double ensureRange(double value, double max, double min) {
        return Math.min(Math.max(value, min), max);
    }

    public static double ensureMax(double value, double max) {
        return Math.min(value, max);
    }

    public static double ensureMin(double value, double min) {
        return Math.max(value, min);
    }

    public static int ensureRange(int value, int max, int min) {
        return Math.min(Math.max(value, min), max);
    }

    public static int ensureMax(int value, int max) {
        return Math.min(value, max);
    }

    public static int ensureMin(int value, int min) {
        return Math.max(value, min);
    }
}
