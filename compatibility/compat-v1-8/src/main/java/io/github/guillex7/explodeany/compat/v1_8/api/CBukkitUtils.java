package io.github.guillex7.explodeany.compat.v1_8.api;

import io.github.guillex7.explodeany.compat.common.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;
import io.github.guillex7.explodeany.compat.common.data.IBossBar;
import io.github.guillex7.explodeany.compat.v1_8.data.CBossBar;

public class CBukkitUtils implements IBukkitUtils {
    @Override
    public IBossBar createBossBar(final String title, final EanyBossBarColor color, final EanyBossBarStyle style) {
        return new CBossBar(title, color, style);
    }
}
