package io.github.guillex7.explodeany.compat.common.environment;

import org.bukkit.Bukkit;

public class Environment {
    public static boolean isPaperBased() {
        final boolean isKnownServer = Environment.isPaper() || Environment.isFolia() || Environment.isPurpur();
        if (isKnownServer) {
            return true;
        }

        try {
            Class.forName("com.destroystokyo.paper.event.entity.CreeperIgniteEvent");
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isPaper() {
        return Bukkit.getServer().getName().toLowerCase().contains("paper");
    }

    public static boolean isFolia() {
        return Bukkit.getServer().getName().toLowerCase().contains("folia");
    }

    public static boolean isPurpur() {
        return Bukkit.getServer().getName().toLowerCase().contains("purpur");
    }
}
