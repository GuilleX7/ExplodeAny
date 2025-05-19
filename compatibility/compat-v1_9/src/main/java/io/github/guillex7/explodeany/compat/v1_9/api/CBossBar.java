package io.github.guillex7.explodeany.compat.v1_9.api;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.compat.common.api.IBossBar;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;

public class CBossBar implements IBossBar {
    BossBar bossBar;

    public CBossBar(String title, EanyBossBarColor color, EanyBossBarStyle style) {
        this.bossBar = Bukkit.createBossBar(title, this.getBarColor(color), this.getBarStyle(style));
    }

    @Override
    public void addPlayer(Player player) {
        this.bossBar.addPlayer(player);
    }

    @Override
    public void removePlayer(Player player) {
        this.bossBar.removePlayer(player);
    }

    @Override
    public void setProgress(double progress) {
        this.bossBar.setProgress(progress);
    }

    private BarColor getBarColor(EanyBossBarColor color) {
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

    private BarStyle getBarStyle(EanyBossBarStyle style) {
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
