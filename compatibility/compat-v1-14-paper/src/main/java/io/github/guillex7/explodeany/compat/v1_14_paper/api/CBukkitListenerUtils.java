package io.github.guillex7.explodeany.compat.v1_14_paper.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.compat.v1_14_paper.listener.CTNTPrimeListener;

public class CBukkitListenerUtils extends io.github.guillex7.explodeany.compat.v1_14.api.CBukkitListenerUtils {
    @Override
    public LoadableListener createTNTPrimeEventListener() {
        return new CTNTPrimeListener();
    }
}
