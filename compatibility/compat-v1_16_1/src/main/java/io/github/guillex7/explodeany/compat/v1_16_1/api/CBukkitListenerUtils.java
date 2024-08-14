package io.github.guillex7.explodeany.compat.v1_16_1.api;

import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class CBukkitListenerUtils implements IBukkitListenerUtils {
    @Override
    public LoadableListener createBlockExplodeListener() {
        return new CBlockExplodeListener();
    }
}
