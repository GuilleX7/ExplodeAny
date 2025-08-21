package io.github.guillex7.explodeany.compat.v1_19_4_paper.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.v1_16_1_paper.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createTNTPrimeEventListener() {
        return new CTNTPrimeListener();
    }
}
