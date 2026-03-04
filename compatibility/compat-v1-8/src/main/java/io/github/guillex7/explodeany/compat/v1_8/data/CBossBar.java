package io.github.guillex7.explodeany.compat.v1_8.data;

import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.data.IBossBar;

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
