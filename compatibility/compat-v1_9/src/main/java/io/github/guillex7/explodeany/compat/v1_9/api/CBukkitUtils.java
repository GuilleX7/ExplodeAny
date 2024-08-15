package io.github.guillex7.explodeany.compat.v1_9.api;

import io.github.guillex7.explodeany.compat.common.api.IBossBar;
import io.github.guillex7.explodeany.compat.common.api.IBukkitUtils;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarColor;
import io.github.guillex7.explodeany.compat.common.data.EanyBossBarStyle;

public class CBukkitUtils implements IBukkitUtils {
    @Override
    public IBossBar createBossBar(String title, EanyBossBarColor color, EanyBossBarStyle style) {
        return new CBossBar(title, color, style);
    }
}
