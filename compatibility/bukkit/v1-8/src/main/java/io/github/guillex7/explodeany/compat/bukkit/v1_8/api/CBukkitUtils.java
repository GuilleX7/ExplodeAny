package io.github.guillex7.explodeany.compat.bukkit.v1_8.api;

import io.github.guillex7.explodeany.compat.bukkit.v1_8.data.CBossBar;
import io.github.guillex7.explodeany.compat.common.bukkit.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.bukkit.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.bukkit.data.IBossBar;

public class CBukkitUtils implements IBukkitUtils {
    @Override
    public IBossBar createBossBar(final String title, final EanyBossBarColor color, final EanyBossBarStyle style) {
        return new CBossBar(title, color, style);
    }
}
