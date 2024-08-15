package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.entity.Player;

public interface IBossBar {
    void addPlayer(Player player);

    void removePlayer(Player player);

    void setProgress(double progress);
}
