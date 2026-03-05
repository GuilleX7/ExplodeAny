package io.github.guillex7.explodeany.compat.bukkit.v1_16_1.api;

import io.github.guillex7.explodeany.compat.bukkit.v1_16_1.listener.CBlockExplodeListener;
import io.github.guillex7.explodeany.compat.common.LoadableListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.bukkit.v1_14.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createBlockExplodeListener() {
        return new CBlockExplodeListener();
    }
}
