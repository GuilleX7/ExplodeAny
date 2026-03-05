package io.github.guillex7.explodeany.compat.common.bukkit.api;

import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.bukkit.data.IBossBar;

public interface IBukkitUtils {
    IBossBar createBossBar(String title, EanyBossBarColor color, EanyBossBarStyle style);
}
