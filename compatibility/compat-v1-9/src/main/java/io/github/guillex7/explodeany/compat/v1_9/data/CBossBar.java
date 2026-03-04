package io.github.guillex7.explodeany.compat.v1_9.data;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.data.IBossBar;

public class CBossBar implements IBossBar {
    BossBar bossBar;

    public CBossBar(final String title, final EanyBossBarColor color, final EanyBossBarStyle style) {
        this.bossBar = Bukkit.createBossBar(title, this.getBarColor(color), this.getBarStyle(style));
    }

    @Override
    public void addPlayer(final Player player) {
        this.bossBar.addPlayer(player);
    }

    @Override
    public void removePlayer(final Player player) {
        this.bossBar.removePlayer(player);
    }

    @Override
    public void setProgress(final double progress) {
        this.bossBar.setProgress(progress);
    }

    private BarColor getBarColor(final EanyBossBarColor color) {
        switch (color) {
            case PINK:
                return BarColor.PINK;
            case BLUE:
                return BarColor.BLUE;
            case RED:
                return BarColor.RED;
            case GREEN:
                return BarColor.GREEN;
            case YELLOW:
                return BarColor.YELLOW;
            case PURPLE:
                return BarColor.PURPLE;
            case WHITE:
                return BarColor.WHITE;
            default:
                return BarColor.WHITE;
        }
    }

    private BarStyle getBarStyle(final EanyBossBarStyle style) {
        switch (style) {
            case SOLID:
                return BarStyle.SOLID;
            case SEGMENTED_6:
                return BarStyle.SEGMENTED_6;
            case SEGMENTED_10:
                return BarStyle.SEGMENTED_10;
            case SEGMENTED_12:
                return BarStyle.SEGMENTED_12;
            case SEGMENTED_20:
                return BarStyle.SEGMENTED_20;
            default:
                return BarStyle.SOLID;
        }
    }
}
