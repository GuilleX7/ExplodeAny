package io.github.guillex7.explodeany.compat.v1_13_paper.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.v1_8_3.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createTNTPrimeEventListener() {
        return new CTNTPrimeListener();
    }
}
