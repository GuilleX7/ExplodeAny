package io.github.guillex7.explodeany.compat.common.environment;

import org.bukkit.Bukkit;

public class Environment {
    public static boolean isPaper() {
        return Bukkit.getServer().getName().toLowerCase().contains("paper");
    }
}
