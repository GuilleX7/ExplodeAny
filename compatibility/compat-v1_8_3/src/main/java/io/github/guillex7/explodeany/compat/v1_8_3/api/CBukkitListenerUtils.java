package io.github.guillex7.explodeany.compat.v1_8_3.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.v1_8.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createBlockExplodeListener() {
        return new CBlockExplodeListener();
    }
}
