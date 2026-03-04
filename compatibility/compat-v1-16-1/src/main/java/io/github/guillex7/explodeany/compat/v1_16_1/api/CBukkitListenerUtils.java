package io.github.guillex7.explodeany.compat.v1_16_1.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.compat.v1_16_1.listener.CBlockExplodeListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.v1_14.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createBlockExplodeListener() {
        return new CBlockExplodeListener();
    }
}
