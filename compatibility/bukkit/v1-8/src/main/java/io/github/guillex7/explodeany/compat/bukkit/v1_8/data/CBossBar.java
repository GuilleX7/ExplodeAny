package io.github.guillex7.explodeany.compat.bukkit.v1_8.data;

import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.bukkit.data.IBossBar;

public class CBossBar implements IBossBar {
    public CBossBar(final String title, final EanyBossBarColor color, final EanyBossBarStyle style) {
        /* Not supported */
    }

    @Override
    public void addPlayer(final Player player) {
        /* Not supported */
    }

    @Override
    public void removePlayer(final Player player) {
        /* Not supported */
    }

    @Override
    public void setProgress(final double progress) {
        /* Not supported */
    }
}
