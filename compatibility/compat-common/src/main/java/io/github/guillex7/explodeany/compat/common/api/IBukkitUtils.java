package io.github.guillex7.explodeany.compat.common.api;

import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.data.IBossBar;

public interface IBukkitUtils {
    IBossBar createBossBar(String title, EanyBossBarColor color, EanyBossBarStyle style);
}
