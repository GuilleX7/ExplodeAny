package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.compat.common.api.IBossBar;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;

public class CBossBar implements IBossBar {
    public CBossBar(String title, EanyBossBarColor color, EanyBossBarStyle style) {
        /* Not supported */
    }

    public void addPlayer(Player player) {
        /* Not supported */
    }

    public void removePlayer(Player player) {
        /* Not supported */
    }

    public void setProgress(double progress) {
        /* Not supported */
    }
}
