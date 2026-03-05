package io.github.guillex7.explodeany.compat.bukkit.v1_9.api;

import io.github.guillex7.explodeany.compat.bukkit.v1_9.data.CBossBar;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.bukkit.data.IBossBar;

public class CBukkitUtils extends io.github.guillex7.explodeany.compat.bukkit.v1_8_3.api.CBukkitUtils {
    @Override
    public IBossBar createBossBar(final String title, final EanyBossBarColor color, final EanyBossBarStyle style) {
        return new CBossBar(title, color, style);
    }
}
