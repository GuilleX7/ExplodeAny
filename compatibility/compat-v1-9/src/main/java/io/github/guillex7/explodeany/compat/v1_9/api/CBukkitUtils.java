package io.github.guillex7.explodeany.compat.v1_9.api;

import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.data.IBossBar;
import io.github.guillex7.explodeany.compat.v1_9.data.CBossBar;

public class CBukkitUtils extends io.github.guillex7.explodeany.compat.v1_8_3.api.CBukkitUtils {
    @Override
    public IBossBar createBossBar(final String title, final EanyBossBarColor color, final EanyBossBarStyle style) {
        return new CBossBar(title, color, style);
    }
}
