package io.github.guillex7.explodeany.compat.v1_20.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.compat.v1_20.listener.CTNTPrimeListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.v1_16_1.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createTNTPrimeEventListener() {
        return new CTNTPrimeListener();
    }
}
