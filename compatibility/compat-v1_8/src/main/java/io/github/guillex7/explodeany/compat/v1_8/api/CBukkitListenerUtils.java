package io.github.guillex7.explodeany.compat.v1_8.api;

import io.github.guillex7.explodeany.compat.common.api.IBukkitListenerUtils;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.compat.v1_8.listener.CBlockExplodeListener;
import io.github.guillex7.explodeany.compat.v1_8.listener.CTNTPrimeListener;

public class CBukkitListenerUtils implements IBukkitListenerUtils {
    @Override
    public LoadableListener createBlockExplodeListener() {
        return new CBlockExplodeListener();
    }

    @Override
    public LoadableListener createTNTPrimeEventListener() {
        return new CTNTPrimeListener();
    }
}
